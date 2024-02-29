import numpy as np

# Q1: Create a zero vector of size 10
def q1():
    x = np.zeros(10)
    return x

# Q2: Create an int64 zero matrix of size (10, 10) with the diagonal values set to -1
def q2():
    x = np.eye(10, dtype=np.int64)
    x *= -1
    return x

# Q3: Create an 8x8 matrix and fill it with a checkerboard pattern with 0s and 1s (starting with 0 at [0, 0])
def q3():
    x = np.zeros((8,8), dtype=int)
    x[1::2, ::2] = 1
    x[::2, 1::2] = 1
    return x

# Q4: Randomly place five 1s in a given zero matrix
def q4(x):
    size = x.size
    random_indicies = np.random.choice(range(size), 5, replace=False)
    x.flat[random_indicies] = 1
    return x

# Q5: Given a tensor (image) of dimensions of (H, W, C), return the same tensor as (C, H, W)
def q5(im):
    im = np.transpose(im, (2,0,1))
    return im

# Q6: Given a tensor (image) of dimension (C, H, W) with channel ordering of RGB, swap the channel ordering to BGR
def q6(im):
    im = im[::-1, :, :]
    return im

# Q7: Given a 1D array, negate (i.e., multiply by -1) all elements in indices [3, 8], in-place
def q7(x):
    x[3:9] *= -1
    return x

# Q8: Convert a float64 array to a uint8 array
def q8(x):
    x = x.astype(np.uint8)
    return x

# Q9: Subtract the mean of each row in the matrix (i.e., subtract the mean of row1 from each element in row1 and continue)
def q9(x):
    N = x.shape[0]
    for i in range(N):
        x[i,:] -= np.mean(x[i,:])
    return x

# Q10: The same as Q9, but without a loop (if you used a loop)
def q10(x):
    x -= x.mean(axis=1, keepdims=True)
    return x

# Q11: Sort the rows of a matrix by the matrix's second column
def q11(x):
    x = x[x[:, 1].argsort()]
    return x

# Q12: Convert an array of size 5 with N=10 (all are values within [0, 9]) to a one-hot encoding matrix as described in the notebook
def q12(x):
    x = np.eye(10)[x]
    return x

# Q13: In a single expression, multiply the n-th row of a given matrix with the n-th element in a given vector.
def q13(x, y):
    return x * y[:, np.newaxis]

# Q14: Without using `np.pad`, pad an array with a border of zeros
def q14(x):
    N = x.shape[0]
    D = x.shape[1]
    pad_arr = np.zeros((N+2, D+2), dtype=int)
    pad_arr[1:-1, 1:-1] = x
    return pad_arr

