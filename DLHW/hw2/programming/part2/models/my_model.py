import torch
import torch.nn as nn


class MyModel(nn.Module):
    # You can use pre-existing models but change layers to recieve full credit.
    def __init__(self):
        super(MyModel, self).__init__()
        #############################################################################
        # TODO: Initialize the network weights                                      #
        #############################################################################
        self.conv_layer_one = nn.Conv2d(3, 32, 3, 1, 1)
        self.batch_normal_one = nn.BatchNorm2d(32)

        self.conv_layer_two = nn.Conv2d(32, 64, 3, 1, 1)
        self.batch_normal_two = nn.BatchNorm2d(64)

        self.conv_layer_three = nn.Conv2d(64, 128, 3, 1, 1)
        self.batch_normal_three = nn.BatchNorm2d(128)

        self.relu = nn.ReLU()                                                                                                                                                                                                                
        self.pool_layer = nn.MaxPool2d(2, 2)

        self.full_connected_five = nn.Linear(2048, 512)
        
        self.full_connected_six = nn.Linear(512, 10)
        #############################################################################
        #                              END OF YOUR CODE                             #
        #############################################################################

    def forward(self, x):
        outs = None
        #############################################################################
        # TODO: Implement forward pass of the network                               #
        #############################################################################
        
        #layer one
        x = self.conv_layer_one(x)
        x = self.batch_normal_one(x)
        x = self.relu(x)
        x = self.pool_layer(x)

        #layer two
        x = self.conv_layer_two(x)
        x = self.batch_normal_two(x)
        x = self.relu(x)
        x = self.pool_layer(x)

        #layer two
        x = self.conv_layer_three(x)
        x = self.batch_normal_three(x)
        x = self.relu(x)
        x = self.pool_layer(x)
        

        x = torch.flatten(x, 1)

        #layer three & four
        x = self.full_connected_five(x)
        x = self.relu(x)
        outs = self.full_connected_six(x)
        #############################################################################
        #                              END OF YOUR CODE                             #
        #############################################################################
        return outs