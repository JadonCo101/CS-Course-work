import torch
import torch.nn as nn

class VanillaCNN(nn.Module):
    def __init__(self):
        super(VanillaCNN, self).__init__()
        #############################################################################
        # TODO: Initialize the Vanilla CNN                                          #
        #       Conv: 7x7 kernel, stride 1 and padding 0                            #
        #       Max Pooling: 2x2 kernel, stride 2                                   #
        #############################################################################

        self.conv_layer = nn.Conv2d(in_channels=3, out_channels=16, kernel_size=7, stride=1, padding=0)

        self.pool_layer = nn.MaxPool2d(kernel_size=2, stride=2, padding=0)
  
        self.num_flat = 16*13*13
        self.relu = nn.ReLU()
        self.fc = nn.Linear(self.num_flat, 10)
        #############################################################################
        #                              END OF YOUR CODE                             #
        #############################################################################


    def forward(self, x):
        outs = None
        #############################################################################
        # TODO: Implement forward pass of the network                               #
        #############################################################################
        
        #conv layer
        x = self.conv_layer(x)

        #relu (comes between conv and pool layer)
        x = self.relu(x)

        #pool layer
        x = self.pool_layer(x)

        #reshape x to fit in fully connected layer
        x = x.view(-1, self.num_flat)

        #fully connected layer
        outs = self.fc(x)

        #############################################################################
        #                              END OF YOUR CODE                             #
        #############################################################################

        return outs