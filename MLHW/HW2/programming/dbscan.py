import numpy as np
from kmeans import pairwise_dist
class DBSCAN(object):
    def __init__(self, eps, minPts, dataset):
        self.eps = eps
        self.minPts = minPts
        self.dataset = dataset
    def fit(self):
        """Fits DBSCAN to dataset and hyperparameters defined in init().
        Args:
            None
        Return:
            cluster_idx: (N, ) int numpy array of assignment of clusters for each point in dataset
        Hint: Using sets for visitedIndices may be helpful here.
        Iterate through the dataset sequentially and keep track of your points' cluster assignments.
        If a point is unvisited or is a noise point (has fewer than the minimum number of neighbor points), then its cluster assignment should be -1.
        Set the first cluster as C = 0
        """
        C = 0
        cluster_idx = np.ones(len(self.dataset), dtype=int) * -1
        
        visitedPoints = set()
        
        for p in range(len(self.dataset)):
            
            if p in visitedPoints:
                continue      
            visitedPoints.add(p)
            
            neighbors = self.regionQuery(p)
            
            
            if len(neighbors) < self.minPts:
                cluster_idx[p] = -1
                
            else:
                C += 1
                self.expandCluster(p, neighbors, C, cluster_idx, visitedPoints)
                             
        return cluster_idx

    def expandCluster(self, index, neighborIndices, C, cluster_idx, visitedIndices):
        """Expands cluster C using the point P, its neighbors, and any points density-reachable to P and updates indices visited, cluster assignments accordingly
           HINT: regionQuery could be used in your implementation
        Args:
            index: index of point P in dataset (self.dataset)
            neighborIndices: (N, ) int numpy array, indices of all points witin P's eps-neighborhood
            C: current cluster as an int
            cluster_idx: (N, ) int numpy array of current assignment of clusters for each point in dataset
            visitedIndices: set of indices in dataset visited so far
        Return:
            None
        Hints:  
            1. np.concatenate(), and np.sort() may be helpful here. A while loop may be better than a for loop.
            2. Use, np.unique(), np.take() to ensure that you don't re-explore the same Indices. This way we avoid redundancy.
        """
        cluster_idx[index] = C
        i = 0
        
        while i < len(neighborIndices):
            
            p_prime = neighborIndices[i]
                
            if p_prime not in visitedIndices:
                visitedIndices.add(p_prime)
                neighborPts_prime = self.regionQuery(p_prime)
                
                if len(neighborPts_prime) >= self.minPts:
                    neighborIndices = np.sort(np.unique(np.concatenate((neighborIndices, neighborPts_prime)),axis=0))
                    i = -1
                if cluster_idx[p_prime] == -1:
                    cluster_idx[p_prime] = C
            
                i += 1

    def regionQuery(self, pointIndex):
        """Returns all points within P's eps-neighborhood (including P)

        Args:
            pointIndex: index of point P in dataset (self.dataset)
        Return:
            indices: (I, ) int numpy array containing the indices of all points within P's eps-neighborhood
        Hint: pairwise_dist (implemented above) and np.argwhere may be helpful here
        """
        dists = pairwise_dist(np.array(self.dataset[[pointIndex], :]), self.dataset)
        neighbors = np.argwhere(dists < self.eps)[:, 1]
        
        return neighbors
        
        
        