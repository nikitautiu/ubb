from __future__ import division  # to implicitly do floats


def get_fuzzy_prob_trapezoid(x, a, b, c, d):
    return max(0, min((x-a)/(b-a), (d-x)/(d-c), 1))


def test_get_fuzzy_prob_trapezoid():
    assert get_fuzzy_prob_trapezoid(37, 15, 30, 30, 55) == 0.72
    assert get_fuzzy_prob_trapezoid(37, 15, 30, 40, 55) == 0.32727272727272727
    assert get_fuzzy_prob_trapezoid(37, 15, 30, 40, 55) == 1
    assert get_fuzzy_prob_trapezoid(54, 15, 30, 40, 55) == 0.06666666666666667
    assert get_fuzzy_prob_trapezoid(55, 15, 30, 40, 55) == 0



