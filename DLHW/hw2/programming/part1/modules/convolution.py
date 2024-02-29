import numpy as np

class Conv2D:
    '''
    An implementation of the convolutional layer. We convolve the input with out_channels different filters
    and each filter spans all channels in the input.
    '''
    def __init__(self, in_channels, out_channels, kernel_size=3, stride=1, padding=0):
        '''
        :param in_channels: the number of channels of the input data
        :param out_channels: the number of channels of the output(aka the number of filters applied in the layer)
        :param kernel_size: the specified size of the kernel(both height and width)
        :param stride: the stride of convolution
        :param padding: the size of padding. Pad zeros to the input with padding size.
        '''
        self.in_channels = in_channels
        self.out_channels = out_channels
        self.kernel_size = kernel_size
        self.stride = stride
        self.padding = padding

        self.cache = None

        self._init_weights()

    def _init_weights(self):
        np.random.seed(1024)
        self.weight = 1e-3 * np.random.randn(self.out_channels, self.in_channels,  self.kernel_size, self.kernel_size)
        self.bias = np.zeros(self.out_channels)

        self.dx = None
        self.dw = None
        self.db = None

    def forward(self, x):
        '''
        The forward pass of convolution
        :param x: input data of shape (N, C, H, W)
        :return: output data of shape (N, self.out_channels, H', W') where H' and W' are determined by the convolution
                 parameters. Save necessary variables in self.cache for backward pass
        '''
        out = None
        #############################################################################
        # TODO: Implement the convolution forward pass.                             #
        # Hint: 1) You may use np.pad for padding.                                  #
        #       2) You may implement the convolution with loops                     #
        #############################################################################
        padding_x = np.pad(x, ((0,0), (0,0), (self.padding, self.padding), (self.padding, self.padding)), mode='constant')

        H_out = ((x.shape[2] + (2*self.padding) - self.weight.shape[2]) // self.stride) + 1
        W_out = ((x.shape[3] + (2*self.padding) - self.weight.shape[3]) // self.stride) + 1
        out = np.zeros((x.shape[0], self.out_channels, H_out, W_out))


        for n in range(x.shape[0]):
          for c in range(self.out_channels):
            for h in range(H_out):
              for w in range(W_out):
                start_h = h*self.stride
                start_w = w*self.stride
                window = padding_x[n, :, start_h:start_h+self.weight.shape[2], start_w:start_w+self.weight.shape[3]]
                out[n, c, h, w] = np.sum(window * self.weight[c, :, :, :]) + self.bias[c]



        #############################################################################
        #                              END OF YOUR CODE                             #
        #############################################################################
        self.cache = x
        return out

    def backward(self, dout):
        '''
        The backward pass of convolution
        :param dout: upstream gradients
        :return: nothing but dx, dw, and db of self should be updated
        '''
        x = self.cache
        
        #############################################################################
        # TODO: Implement the convolution backward pass.                            #
        # Hint:                                                                     #
        #       1) You may implement the convolution with loops                     #
        #       2) don't forget padding when computing dx                           #
        #############################################################################
        
        #print(pad_dout.shape)
        x_pad = np.pad(x, ((0, 0), (0, 0), (self.padding, self.padding), (self.padding, self.padding)), mode='constant')
        dx = np.zeros((x.shape[0], x.shape[1], x.shape[2], x.shape[3]))
        dx = np.pad(dx, ((0,0), (0,0), (self.padding, self.padding), (self.padding, self.padding)), mode = 'constant')

        dw = np.zeros(self.weight.shape)
        db = np.zeros(self.bias.shape)
      
        for n in range(x.shape[0]):
          for c in range(self.weight.shape[0]):
            for h in range(dout.shape[2]):
              for w in range(dout.shape[3]):
                start_height = h * self.stride
                end_height = start_height + self.weight.shape[2]
                start_width = w * self.stride
                end_width = start_width + self.weight.shape[3]

                dw[c] += x_pad[n, :, start_height:end_height, start_width:end_width] * dout[n, c, h, w]
                
                dx[n, :, start_height:end_height, start_width:end_width] += self.weight[c] * dout[n, c, h, w]
                
                
                db[c] += dout[n, c, h, w]

        
        self.dx = dx[:, :, self.padding:-self.padding, self.padding:-self.padding]
        self.db = db
        self.dw = dw
        #print(self.dx)
        #print('lol')
        #print(padding_dx.shape)
        #############################################################################
        #                              END OF YOUR CODE                             #
        #############################################################################