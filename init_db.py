#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import pymysql as mdb
import os
import random
import hashlib

if __name__ == '__main__':

    DB_NAME = 'awesome_tickets'
    USER = 'root'
    PSWD = '123456'
    SHOW_DATE = ['2017-05-01', '2017-05-02', '2017-05-03']
    SHOW_TIME = ['10:05:00', '13:20:00', '16:35:00', '19:50:00', '22:05:00']
    TICKET_PRICE = [20.0, 22.5, 28, 35, 37, 41.5]
    RAND_SOLD_SEATS = True
    TEST_PHONE = '18812345678'

    os.system("mysql -u%s -p%s < ./script/init.sql" % (USER, PSWD))

    conn = mdb.connect(host='localhost',
                       user=USER,
                       password=PSWD,
                       db=DB_NAME,
                       charset='utf8')

    try:
        with conn.cursor() as cursor:

            # Print database info
            cursor.execute("SELECT VERSION()")
            data = cursor.fetchone()
            print("MySQL version: %s" % data)

            # Find available movies
            print("Finding available movies...")
            cursor.execute("""
                SELECT movie_id, name
                FROM (movie NATURAL JOIN movie_status) NATURAL JOIN country
                WHERE status='on'
            """)
            movies = []
            for entry in cursor.fetchall():
                entry = list(entry)
                if (entry[1] != u"中国"):
                    entry[1] = u"英语"
                else:
                    entry[1] = u"国语"
                movies.append(entry)
            print("movies:", movies)

            # Find available cinema halls
            print("Finding available cinema halls...")
            cursor.execute("""
                SELECT cinema_hall_id
                FROM cinema_hall
            """)
            cinema_hall_ids = []
            for entry in cursor.fetchall():
                cinema_hall_ids.append(entry[0])
            print("cinema_hall_ids:", cinema_hall_ids)

            # Add on show movies
            print("Adding on show movies...")
            candidate_movies = []
            for cinema_hall_id in cinema_hall_ids:
                for date in SHOW_DATE:
                    for time in SHOW_TIME:
                        if (len(candidate_movies) == 0):
                            candidate_movies = list(movies)
                        pos = random.randrange(len(candidate_movies))
                        movie = candidate_movies.pop(pos)
                        price = TICKET_PRICE[random.randrange(
                            len(TICKET_PRICE))]
                        cursor.execute("""
                            INSERT INTO movie_on_show
                            (movie_id, cinema_hall_id, lang,
                             show_date, show_time, price)
                            VALUES (%d, %d, '%s', '%s', '%s', %f)
                        """ % (movie[0], cinema_hall_id, movie[1],
                               date, time, price))

            # Add seats
            print("Adding seats...")
            cursor.execute("""
                SELECT movie_on_show_id, seat_layout
                FROM movie_on_show NATURAL JOIN cinema_hall
            """)
            for entry in cursor.fetchall():
                movie_on_show_id = entry[0]
                seat_layout = entry[1]
                seat_rows = seat_layout.split(',')
                for i, row in enumerate(seat_rows):
                    col = 1
                    for char in row:
                        if RAND_SOLD_SEATS:
                            available = random.randrange(5)
                        else:
                            available = 1
                        if (char != '0'):
                            cursor.execute("""
                                INSERT INTO seat
                                (movie_on_show_id, row, col, available)
                                VALUES (%d, %d, %d, %d)
                            """ % (movie_on_show_id, i + 1, col, available))
                            col += 1

            # Add test user
            print("Adding test user...")
            cursor.execute("""
                INSERT INTO user (phone_num) VALUES (%s)
            """ % TEST_PHONE)

            # Add test ticket
            print("Adding test ticket...")
            cursor.execute("INSERT INTO ticket (user_id) VALUES (1)")
            cursor.execute("SELECT LAST_INSERT_ID()")
            ticket_id = cursor.fetchall()[0][0]
            digest = hashlib.md5(str(ticket_id).encode("utf-8")).hexdigest()
            cursor.execute("""
                UPDATE ticket SET digest='%s' WHERE ticket_id=%d
            """ % (digest, ticket_id))

            # Bind ticket to seats
            print("Binding ticket to seats...")
            cursor.execute("""
                UPDATE seat SET ticket_id=%d WHERE seat_id in (1, 2, 3)
            """ % ticket_id)

        conn.commit()
        print("Done.")
    finally:
        conn.close()
