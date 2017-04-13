#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import pymysql as mdb
import os
import random

user = 'root'
pswd = '123456'
show_date = ['2017-04-04', '2017-04-05', '2017-04-06']
show_time = ['10:05:00', '13:20:00', '16:35:00', '19:50:00', '22:05:00']
prices = [20.0, 22.5, 28, 35, 37, 41.5]

os.system("mysql -u%s -p%s < ./script/init.sql" % (user, pswd))

conn = mdb.connect(host='localhost',
                   user=user,
                   password=pswd,
                   db='tickets',
                   charset='utf8')

try:
    # Print database infos
    with conn.cursor() as cursor:
        cursor.execute("SELECT VERSION()")
        data = cursor.fetchone()
        print("Database version: %s" % data)
    print("Initializing...")
    # Add simulated movies on show
    with conn.cursor() as cursor:
        cursor.execute("""
            SELECT movieID, name
            FROM (movie NATURAL JOIN movie_status) NATURAL JOIN country
            WHERE status='on'
        """)
        movies = []
        for entry in cursor.fetchall():
            entry = list(entry)
            if (entry[1] != "中国"):
                entry[1] = "英语"
            else:
                entry[1] = "国语"
            movies.append(entry)
        # print("movies:", movies)

        cursor.execute("""
            SELECT cinemaHallID
            FROM cinema_hall
        """)
        cinema_hall_ids = []
        for entry in cursor.fetchall():
            cinema_hall_ids.append(entry[0])
        # print("cinema_hall_ids:", cinema_hall_ids)

        candidate_movies = movies.copy()
        for cinema_hall_id in cinema_hall_ids:
            for date in show_date:
                for time in show_time:
                    if (len(candidate_movies) == 0):
                        candidate_movies = movies.copy()
                    pos = random.randrange(len(candidate_movies))
                    movie = candidate_movies.pop(pos)
                    price = prices[random.randrange(len(prices))]
                    cursor.execute("""
                        INSERT INTO movie_on_show
                        (movieID, cinemaHallID, lang,
                         showDate, showTime, price)
                        VALUES (%d, %d, '%s', '%s', '%s', %f)
                    """ % (movie[0], cinema_hall_id, movie[1],
                           date, time, price))
    conn.commit()
    # Add seats
    with conn.cursor() as cursor:
        cursor.execute("""
            SELECT movieOnShowID, seatLayout
            FROM movie_on_show NATURAL JOIN cinema_hall
        """)
        for entry in cursor.fetchall():
            movie_on_show_id = entry[0]
            seat_layout = entry[1]
            seat_rows = seat_layout.split(',')
            for i, row in enumerate(seat_rows):
                col = 1
                for char in row:
                    if (char != '0'):
                        cursor.execute("""
                            INSERT INTO seat(movieOnShowID, row, col) VALUES
                            (%d, %d, %d)
                        """ % (movie_on_show_id, i + 1, col))
                        col += 1
    conn.commit()
    print("Finished.")
finally:
    conn.close()
