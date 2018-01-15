#!/usr/bin/env bash
source "${PYTHON_VIRTUALENV}/bin/activate"
mpiexec -n 6 python test.py