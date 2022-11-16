import threading
import random
import time


lock = threading.RLock()
condition = threading.Condition()
number_of_philosopher = 5


class DiningPhilosopher(threading.Thread):

    def __init__(self, philosopher_id):
        threading.Thread.__init__(self)
        self.philosopher_id = philosopher_id
        self.chopsticks = [threading.RLock()
                           for _ in range(number_of_philosopher)]

    def run(self):
        lock.acquire()
        takeFork(self.philosopher_id)
        eat(self.philosopher_id)
        returnFork(self.philosopher_id)
        lock.release()


state = [None for i in range(number_of_philosopher)]

for item in range(number_of_philosopher):
    state[item] = "THINKING"
    print(f"Philosopher {item} is thinking...")


def eat(id):
    print(f"Philosopher {id} is eating...")
    time.sleep(1)


def takeFork(id):
    lock.acquire()
    state[id] = "HUNGRY"
    print(f"Philosopher {id} is hungry")
    test(id)

    while state[id] != "EATING":
        condition.wait()

    lock.release()


def test(id):
    if state[(id+4) % 5] != "EATING" and state[id] == "HUNGRY" and state[(id + 1) % 5] != "EATING":
        condition.acquire()
        state[id] = "EATING"
        condition.release()


def returnFork(i):
    condition.acquire()
    state[i] = "THINKING"
    test((i + 4) % 5)
    test((i + 1) % 5)
    condition.release()


if __name__ == "__main__":

    th = [None] * number_of_philosopher

    for i in range(number_of_philosopher):
        th[i] = DiningPhilosopher(i)
        th[i].start()

    for i in range(number_of_philosopher):
        th[i].join()
