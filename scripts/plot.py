"""Turns JMH JSON results in results/ into charts."""
import json
import sys


def main(path):
    with open(path) as f:
        data = json.load(f)
    # TODO: group by benchmark / abstraction / threads and plot speedup
    print(f"{len(data)} results loaded")


if __name__ == "__main__":
    main(sys.argv[1])
