import random


def ai_default(_):
    return "default"


def ai_bandit_leader(last_skill):
    return {
        "reckless_attack": "assault",
        "assault": "defense"
    }.get(last_skill, "reckless_attack")


def ai_slime(last_skill):
    return {
        "splashing1": "splashing2",
        "tentacle1": "tentacle2"
    }.get(last_skill, random.choice(["splashing1", "tentacle1", "slime_jab"]))
    # if last_skill == "splashing1":
    #     return "splashing2"
    # elif last_skill == "tentacle1":
    #     return "tentacle2"
    # else:
    #     return random.choice(["splashing1", "tentacle1", "slime_jab"])
