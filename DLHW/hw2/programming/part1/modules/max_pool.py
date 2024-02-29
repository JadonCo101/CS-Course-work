import numpy as np

class MaxPooling:
    '''
    Max Pooling of input
    '''
    def __init__(self, kernel_size, stride):
        self.kernel_size = kernel_size
        self.stride = stride
        self.cache = None
        self.dx = None

    def forward(self, x):
        '''
        Forward pass of max pooling
        :param x: input, (N, C, H, W)
        :return: The output by max pooling with kernel_size and stride
        '''
        out = None
        #############################################################################
        # TODO: Implement the max pooling forward pass.                             #
        # Hint:                                                                     #
        #       1) You may implement the process with loops                         #
        #############################################################################
        H_out = (x.shape[2] - self.kernel_size) // self.stride + 1
        W_out = (x.shape[3] - self.kernel_size) // self.stride + 1


        out = np.zeros((x.shape[0], x.shape[1], H_out, W_out))

        for n in range(x.shape[0]):
          for c in range(x.shape[1]):
            for h in range(H_out):
              for w in range(W_out):
                start_height = h * self.stride
                end_height = start_height + self.kernel_size
                start_width = w * self.stride
                end_width = start_width + self.kernel_size
                out[n,c,h,w] = np.max(x[n,c,start_height:end_height, start_width:end_width])
          
        #############################################################################
        #                              END OF YOUR CODE                             #
        #############################################################################
        self.cache = (x, H_out, W_out)
        return out

    def backward(self, dout):
        '''
        Backward pass of max pooling
        :param dout: Upstream derivatives
        :return:
        '''
        x, H_out, W_out = self.cache
        #############################################################################
        # TODO: Implement the max pooling backward pass.                            #
        # Hint:                                                                     #
        #       1) You may implement the process with loops                     #
        #       2) You may find np.unravel_index useful                             #
        #############################################################################
        dx = np.zeros(x.shape)
        for n in range(x.shape[0]):
          for c in range(x.shape[1]):
            for h in range(H_out):
              for w in range(W_out):
                start_height = h * self.stride
                end_height = start_height + self.kernel_size
                start_width = w * self.stride
                end_width = start_width + self.kernel_size
                section = x[n, c, start_height:end_height, start_width:end_width]
                maximum_index = np.unravel_index(np.argmax(section), section.shape)
                dx[n,c, start_height + maximum_index[0], start_width + maximum_index[1]] = dout[n,c,h,w]
        self.dx = dx
        #############################################################################
        #                              END OF YOUR CODE                             #
        #############################################################################
