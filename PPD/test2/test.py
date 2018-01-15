from mpi4py import MPI

comm = MPI.COMM_WORLD
size = comm.Get_size()
rank = comm.Get_rank()

N = size - 1  # the rank of the polinomial

if rank == 0:
    # reading and sending the coefficients
    with open('polinom.txt') as fin:
        coefs = list(map(int, fin.readline().split(' ')[:N]))  # read the coefs
    for coef, proc_id in zip(coefs, range(1, N + 1)):
        comm.send(coef, dest=proc_id)  # send the coeeficient

    # read the values to send
    with open('input.txt') as fin:
        values = list(map(int, fin.readline().split(' ')))

    # broadcast how many values will send
    comm.bcast(len(values), root=0)

    # send and wait for response
    for value in values:
        comm.bcast(value, root=0)  # send the value of x
        comm.send(0, dest=1)  # first one receives a0
        result = comm.recv(source=N)  # receive the value from the last one
        print('P({}) = {}'.format(value, result))
else:
    # wait for the coefficient
    coef = comm.recv(source=0)
    n = comm.bcast(None, root=0)  # how many values to wait for

    # open the file if the last one
    fout = None
    if rank == N:
        fout = open("output.txt", 'w')

    for _ in range(n):
        x = comm.bcast(None, root=0)  # receive the last one
        value = comm.recv(source=rank - 1)  # recieve from the last one(blocking)

        result = x * value + coef  # horner
        if rank == N:
            fout.write('{} '.format(result))
            comm.send(result, dest=0)  # send it to root(for debug)
        else:
            comm.send(result, dest=rank + 1)  # send to the next one

    if rank == N:
        # close the file
        fout.close()
