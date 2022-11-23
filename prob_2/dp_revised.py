import random
import threading
import time

"""
Code taken from Prof.Qian's Lecture

"""


class DP:
    def __init__(self, count) -> None:
        self._states = ['THINKING'] * count
        self._count = count
        self._lock = threading.RLock()
        self._condition_variable = [threading.Condition(self._lock)]*count
        self.oLock = threading.Lock()

    def takeForks(self, i):
        with self._lock:
            self._states[i] = "HUNGRY"
            self.test(i)
            if self._states[i] != "EATING":
                self._condition_variable[i].wait()

    def returnForks(self, i):
        with self._lock:
            self._states[i] = "THINKING"
            self.test((i-1) % self._count)
            self.test((i+1) % self._count)

    def test(self, i):
        with self._lock:
            if (self._states[(i-1) % self._count] != "EATING" and
                    self._states[i] == "HUNGRY" and self._states[(i + 1) % self._count] != "EATING"):
                self._states[i] = "EATING"
                self._condition_variable[i].notify()


def philosopher(i, dp, n):
    for _ in range(n):
        time.sleep(random.random())

        with dp.oLock:
            print("Philosopher ", i, "is Hungry")
        dp.takeForks(i)

        with dp.oLock:
            print("Philosopher ", i, " eating")

        dp.returnForks(i)

        with dp.oLock:
            print("Philosopher ", i, " is thinking")


dp = DP(5)

for i in range(5):
    threading.Thread(target=philosopher, args=(i, dp, 10)).start()
