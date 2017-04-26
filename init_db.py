#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import pymysql as mdb
import os
import random

if __name__ == '__main__':

    DB_NAME = 'awesome_tickets'
    USER = 'root'
    PSWD = '123456'
    SHOW_DATE = ['2017-05-01', '2017-05-02', '2017-05-03']
    SHOW_TIME = ['10:05:00', '13:20:00', '16:35:00', '19:50:00', '22:05:00']
    TICKET_PRICE = [20.0, 22.5, 28, 35, 37, 41.5]
    RAND_SOLD_SEATS = True

    os.system("mysql -u%s -p%s < ./script/init.sql" % (USER, PSWD))

    conn = mdb.connect(host='localhost',
                       user=USER,
                       password=PSWD,
                       db=DB_NAME,
                       charset='utf8')

    try:
        # Print database infos
        with conn.cursor() as cursor:
            cursor.execute("SELECT VERSION()")
            data = cursor.fetchone()
            print("Database version: %s" % data)
        print("Initializing...")
        print("Adding movies on show...")
        with conn.cursor() as cursor:
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

            cursor.execute("""
                SELECT cinema_hall_id
                FROM cinema_hall
            """)
            cinema_hall_ids = []
            for entry in cursor.fetchall():
                cinema_hall_ids.append(entry[0])
            print("cinema_hall_ids:", cinema_hall_ids)

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
        conn.commit()
        print("Adding seats...")
        with conn.cursor() as cursor:
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
        conn.commit()
        print("Finished.")
    finally:
        conn.close()
