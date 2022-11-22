import math
from random import random
import threading
from time import sleep


class Producer(threading.Thread):

    def __init__(self, sl, size) -> None:
        threading.Thread.__init__(self)
        self.sl = sl
        self.size = size

    def run(self) -> None:
        number_in_circle = 0

        for i in range(100):
            x = random()
            y = random()
            distance = math.sqrt(x**2 + y**2)
            if distance <= 1:
                number_in_circle += 1

        self.insert(number_in_circle)

    def insert(self, n):
        while (self.sl[-1] == self.size):
            print(".", end="")
            sleep(0.01)

        self.sl[sl[-3]] = n
        self.sl[-3] = (self.sl[-3] + 1) % self.size
        self.sl[-1] += 1


def consume(sl, size):

    while (sl[-1] == 0):
        print(",", end="")
        sleep(0.01)

    number_in_circle = sl[sl[-2]]

    myPI = 4.0 * number_in_circle / 100
    print("Estimated pi is: ", myPI)

    sl[-2] = (sl[-2] + 1) % size
    sl[-1] -= 1


if __name__ == "__main__":
    size = 5
    th = [None] * 10
    sl = [0 for _ in range(size+3)]

    for i in range(10):
        th[i] = Producer(sl, size)
        th[i].start()

    consume(sl, size)

    for i in range(10):
        th[i].join()
