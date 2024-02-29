
'''
File: kmeans.py
Project: Downloads
File Created: Feb 2021
Author: Rohit Das
'''

import numpy as np


class KMeans(object):

    def __init__(self, points, k, init='random', max_iters=10000, rel_tol=1e-5):  # No need to implement
        """
        Args:
            points: NxD numpy array, where N is # points and D is the dimensionality
            K: number of clusters
            init : how to initial the centers
            max_iters: maximum number of iterations (Hint: You could change it when debugging)
            rel_tol: convergence criteria with respect to relative change of loss (number between 0 and 1)
        Return:
            none
        """
        self.points = points
        self.K = k
        if init == 'random':
            self.centers = self.init_centers()
        else:
            self.centers = self.kmpp_init()
        self.assignments = None
        self.loss = 0.0
        self.rel_tol = rel_tol
        self.max_iters = max_iters

       
        
        return
    def init_centers(self):# [2 pts]
        """
        Initialize the centers randomly
        Return:
        self.centers : K x D numpy array, the centers.
        Hint: Please initialize centers by randomly sampling points from the
        dataset in case the autograder fails.
        """
        
        
        random_int = np.random.choice(np.arange(len(self.points)), self.K, replace=False)
        self.centers = self.points[random_int]
        
        return self.centers
        
    def kmpp_init(self):# [3 pts]
        """
            Use the intuition that points further away from each other will probably be better initial centers
        Return:
            self.centers : K x D numpy array, the centers.
        """
        
        raise NotImplementedError
        
        
    def update_assignment(self):  # [5 pts]
        """
            Update the membership of each point based on the closest center
        Return:
            self.assignments : numpy array of length N, the cluster assignment for each point
        Hint: You could call pairwise_dist() function
        Hint: In case the np.sqrt() function is giving an error in the pairwise_dist() function, you can use the squared distances directly for comparison. 
        """        

        
        dist_to_center = pairwise_dist(self.points, self.centers)
        self.assignments = np.argmin(dist_to_center, axis=1)
        
        
        return self.assignments

    def update_centers(self):  # [5 pts]
        """
            update the cluster centers
        Return:
            self.centers: new centers, a new K x D numpy array of float dtype, where K is the number of clusters, and D is the dimension.

        HINT: Points may be integer, but the centers should not have to be. Watch out for dtype casting!
        """
        
        new_centers = []
        
        for k in np.unique(self.assignments):
            
            idx = np.where(self.assignments == k)[0]
            single_center = np.mean(self.points[idx],axis=0)
            new_centers.append(single_center)
            
        self.centers = np.array(new_centers)
        
        
        return self.centers
        
        #other implementation that doesn't work properly
        
        #New array for centers with shape K x D and float dtype
        #new_centers = np.zeros((np.shape(self.centers)[0], self.points.shape[1]), dtype=float)  
        
        #Uses Bincount to calculate number of data points in each cluster
        #clustersSum = np.bincount(self.assignments)
        
        #used np.add.at to add all data points (with specific self.assignment cluster assignment) to new_centers
        #np.add.at(new_centers, self.assignments, self.points)
        
        #update cluster centers by dividing sum of points in cluster by number of points
        #self.centers = new_centers / clustersSum[:, np.newaxis]
        
        

    def get_loss(self):  # [5 pts]
        """
            The loss will be defined as the sum of the squared distances between each point and it's respective center.
        Return:
            self.loss: a single float number, which is the objective function of KMeans.
        """
        
        
        self.loss = 0.0
        for k in range(self.centers.shape[0]):
            indicies = np.where(self.assignments == k)[0]
            if len(indicies) > 0:
                
                dist = (self.centers[k] - self.points[indicies])**2

                self.loss += (np.sum(dist))
        
        return self.loss


    def train(self):    # [10 pts]
        """
            Train KMeans to cluster the data:
                0. Recall that centers have already been initialized in __init__
                1. Update the cluster assignment for each point
                2. Update the cluster centers based on the new assignments from Step 1
                3. Check to make sure there is no mean without a cluster, 
                   i.e. no cluster center without any points assigned to it.
                   - In the event of a cluster with no points assigned, 
                     pick a random point in the dataset to be the new center and 
                     update your cluster assignment accordingly.
                4. Calculate the loss and check if the model has converged to break the loop early.
                   - The convergence criteria is measured by whether the percentage difference 
                     in loss compared to the previous iteration is less than the given 
                     relative tolerance threshold (self.rel_tol). 
                   - Relative tolerance threshold (self.rel_tol) is a number between 0 and 1.   
                5. Iterate through steps 1 to 4 max_iters times. Avoid infinite looping!
                
        Return:
            self.centers: K x D numpy array, the centers
            self.assignments: Nx1 int numpy array
            self.loss: final loss value of the objective function of KMeans.
        """
        
        prev_loss= -100.0
        for i in range(0, self.max_iters):
            #Step 1:
            
            self.update_assignment()
        
            if len(self.centers) < self.K:
                
                rand_points = np.random.default_rng().choice(self.points, self.K-len(self.centers))
                self.centers = np.vstack((self.centers, rand_points))
                
                
            #Step 2:
            while len(self.centers) < self.K: #we have (K-current_amount) empty clusters
                self.update_assignment()
                
                
                rand_points = np.random.default_rng().choice(self.points, self.K-len(self.centers))
                self.centers = np.vstack((self.centers, rand_points))
                  
                
                
            #Step 3:
            self.update_centers()
                   
            #Step 4:
            curr_loss = self.get_loss()
            
            #Step 5:
            
            if prev_loss == -100.0:
                prev_loss = curr_loss
            else:
                check = np.abs(prev_loss - curr_loss)
                if check / prev_loss < self.rel_tol:
                    break
                prev_loss = curr_loss
            
        
        return self.centers, self.assignments, self.loss
        


def pairwise_dist(x, y):  # [5 pts]
        np.random.seed(1)
        """
        Args:
            x: N x D numpy array
            y: M x D numpy array
        Return:
                dist: N x M array, where dist2[i, j] is the euclidean distance between
                x[i, :] and y[j, :]
        """
        #(a-b)^2 = a^2 - 2ab + b^2
        
        x_squared = np.sum(np.square(x),axis=1)
        y_squared = np.sum(np.square(y),axis=-1)
        mult = -2 * np.dot(x, np.transpose(y))
        
        ed = np.sqrt(np.abs(x_squared[:, np.newaxis] + y_squared + mult))
        
        return ed

def rand_statistic(xGroundTruth, xPredicted): # [5 pts]
    """
    Args:
        xPredicted : N x 1 numpy array, N = no. of test samples
        xGroundTruth: N x 1 numpy array, N = no. of test samples
    Return:
        Rand statistic value: final coefficient value as a float
    """
    
    same_clust = 0
    diff_clust = 0
    
    for i in range(0, len(xGroundTruth)):
        for j in range(i+1, len(xGroundTruth)):
            
            if (xGroundTruth[i] == xGroundTruth[j]) and (xPredicted[i] == xPredicted[j]):
                same_clust += 1
            elif (xGroundTruth[i] != xGroundTruth[j]) and (xPredicted[i] != xPredicted[j]):
                diff_clust += 1
            else:
                continue
            
            
    N = len(xGroundTruth)       
    rand_stat = ((same_clust + diff_clust) / ((N*(N-1)/2)))
                 
    
    return rand_stat   
    
    
    
  