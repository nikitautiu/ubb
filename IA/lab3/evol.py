import random


def crossover(chrom1, chrom2, co_prob):
    """Crossover uniform"""
    if random.random() < co_prob:
        bit_chrom1 = int(chrom1, 2)
        bit_chrom2 = int(chrom2, 2)

        mask = random.randint(1, 2**len(chrom1) - 1)
        bit_result = (bit_chrom1 & mask) + (bit_chrom2 & (~mask))
        return ('{0:0' + str(len(chrom1)) + 'b}').format(bit_result)
    else:
        return random.choice([chrom1, chrom2])

