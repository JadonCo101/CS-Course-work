import numpy as np
from tqdm import tqdm
from kmeans import KMeans


SIGMA_CONST = 1e-6
LOG_CONST = 1e-32

FULL_MATRIX = False # Set False if the covariance matrix is a diagonal matrix

class GMM(object):
    def __init__(self, X, K, max_iters=100):  # No need to change
        """
        Args:
            X: the observations/datapoints, N x D numpy array
            K: number of clusters/components
            max_iters: maximum number of iterations (used in EM implementation)
        """
        self.points = X
        self.max_iters = max_iters

        self.N = self.points.shape[0]  # number of observations
        self.D = self.points.shape[1]  # number of features
        self.K = K  # number of components/clusters

    # Helper function for you to implement
    def softmax(self, logit):  # [5pts]
        """
        Args:
            logit: N x D numpy array
        Return:
            prob: N x D numpy array. See the above function.
        Hint:
            Add keepdims=True in your np.sum() function to avoid broadcast error. 
        """
        
        #find max and subtract from logit
        upperBound = np.max(logit, axis=1, keepdims=True)
        logit -= upperBound
        
        
        exp_logit = np.exp(logit)
                           
                           
        sum_logit = np.sum(exp_logit, axis=1, keepdims=True)
                           
        prob = exp_logit / sum_logit

        return prob

    def logsumexp(self, logit):  # [5pts]
        """
        Args:
            logit: N x D numpy array
        Return:
            s: N x 1 array where s[i,0] = logsumexp(logit[i,:]). See the above function
        Hint:
            The keepdims parameter could be handy
        """

        upperBound = np.max(logit, axis=1, keepdims=True)
        logit -= upperBound
        
        exp_logit = np.exp(logit)
        
        sum_logit = np.sum(exp_logit, axis=1, keepdims=True)
        s = upperBound + np.log(sum_logit)

        return s
        
    # for undergraduate student
    def normalPDF(self, points, mu_i, sigma_i):  # [5pts]
        """
        Args:
            points: N x D numpy array
            mu_i: (D,) numpy array, the center for the ith gaussian.
            sigma_i: DxD numpy array, the covariance matrix of the ith gaussian.
        Return:
            pdf: (N,) numpy array, the probability density value of N data for the ith gaussian

        Hint:
            np.diagonal() should be handy.
        """
        
        variance = (np.diagonal((sigma_i))) #(D,)
        
        first = 1/(np.sqrt(2 * np.pi * variance)) # (D,)
        
        
        exponent = -0.5 * np.square(points - mu_i) * (1 / variance) #(N,D) - (D,) / (D,) -> (N,D)
        
        pdf = first * (np.exp(exponent)) #(D,) * NxD -> NxD
        
        
         #(N,)
        answer = np.prod(pdf, axis=1)
        
        
        return answer
        

    # for grad students
    def multinormalPDF(self, points, mu_i, sigma_i):  # [5pts]
        """
        Args:
            points: N x D numpy array
            mu_i: (D,) numpy array, the center for the ith gaussian.
            sigma_i: DxD numpy array, the covariance matrix of the ith gaussian.
        Return:
            normal_pdf: (N,) numpy array, the probability density value of N data for the ith gaussian

        Hint:
            1. np.linalg.det() and np.linalg.inv() should be handy.
            2. The value in self.D may be outdated and not correspond to the current dataset,
            try using another method involving the current arguments to get the value of D
        """

        raise NotImplementedError


    def create_pi(self):
        """
        Initialize the prior probabilities 
        Args:
        Return:
        pi: numpy array of length K, prior
        """
        
        return np.ones(self.K) / self.K

    def create_mu(self):
        """
        Intialize random centers for each gaussian
        Args:
        Return:
        mu: KxD numpy array, the center for each gaussian.
        """

        L = len(self.points)
        idx = np.random.choice(L, size=self.K,replace=False)
        mu = self.points[idx]
        
        return mu
    
    def create_sigma(self):
        """
        Initialize the covariance matrix with np.eye() for each k. For grads, you can also initialize the 
        by K diagonal matrices.
        Args:
        Return:
        sigma: KxDxD numpy array, the diagonal standard deviation of each gaussian.
            You will have KxDxD numpy array for full covariance matrix case
        """
        
        
        sigma = np.stack([np.eye(self.D) for k in range(self.K)])
        
        return sigma
    
    def _init_components(self, **kwargs):  # [5pts]

        """
        Args:
            kwargs: any other arguments you want
        Return:
            pi: numpy array of length K, prior
            mu: KxD numpy array, the center for each gaussian.
            sigma: KxDxD numpy array, the diagonal standard deviation of each gaussian.
                You will have KxDxD numpy array for full covariance matrix case

            Hint: np.random.seed(5) may be used at the start of this function to ensure consistent outputs.
        """
        np.random.seed(5) #Do Not Remove Seed
                        
                        
        pi = self.create_pi()
        
        mu = self.create_mu()
        
        sigma = self.create_sigma()
        
        return pi, mu, sigma

    def _ll_joint(self, pi, mu, sigma, full_matrix=FULL_MATRIX, **kwargs):  # [10 pts]
        """
        Args:
            pi: np array of length K, the prior of each component
            mu: KxD numpy array, the center for each gaussian.
            sigma: KxDxD numpy array, the diagonal standard deviation of each gaussian. You will have KxDxD numpy
            array for full covariance matrix case
            full_matrix: whether we use full covariance matrix in Normal PDF or not. Default is True.

        Return:
            ll(log-likelihood): NxK array, where ll(i, k) = log pi(k) + log NormalPDF(points_i | mu[k], sigma[k])
        """
        
        
        if full_matrix is False:
            log_liklihood = np.empty((self.points.shape[0], self.K))
            
            for k in range(0, self.K):
                log_liklihood_k = np.log(pi[k] + LOG_CONST)
                log_liklihood_k += np.log(self.normalPDF(self.points, mu[k], sigma[k]) + LOG_CONST)
                
                
                log_liklihood[:,k] = log_liklihood_k
                
            return log_liklihood
            
        
                            
        

    def _E_step(self, pi, mu, sigma, full_matrix = FULL_MATRIX , **kwargs):  # [5pts]
        """
        Args:
            pi: np array of length K, the prior of each component
            mu: KxD numpy array, the center for each gaussian.
            sigma: KxDxD numpy array, the diagonal standard deviation of each gaussian.You will have KxDxD numpy
            array for full covariance matrix case
            full_matrix: whether we use full covariance matrix in Normal PDF or not. Default is True.
        Return:
            gamma(tau): NxK array, the posterior distribution (a.k.a, the soft cluster assignment) for each observation.

        Hint:
            You should be able to do this with just a few lines of code by using _ll_joint() and softmax() defined above.
        """
        # === graduate implementation
        #if full_matrix is True:
            # ...

        # === undergraduate implementation
        if full_matrix is False:
            return self.softmax(self._ll_joint(pi, mu, sigma))

    def _M_step(self, gamma, full_matrix=FULL_MATRIX, **kwargs):  # [10pts]
        """
        Args:
            gamma(tau): NxK array, the posterior distribution (a.k.a, the soft cluster assignment) for each observation.
            full_matrix: whether we use full covariance matrix in Normal PDF or not. Default is True.
        Return:
            pi: np array of length K, the prior of each component
            mu: KxD numpy array, the center for each gaussian.
            sigma: KxDxD numpy array, the diagonal standard deviation of each gaussian. You will have KxDxD numpy
            array for full covariance matrix case

        Hint:
            There are formulas in the slides and in the Jupyter Notebook.
            Undergrads: To simplify your calculation in sigma, make sure to only take the diagonal terms in your covariance matrix
        """
        # === graduate implementation
        #if full_matrix is True:
            # ...

        # === undergraduate implementation
        if full_matrix is False:
            N_k = np.sum(gamma, axis=0) 
            mu = []

            #pi: np array of length K, the prior of each component
            pi = (np.sum(gamma, axis=0) ) / (np.sum(N_k, axis=0))

            #mu: KxD array, the center for each gaussian ()
            gamTranspose = np.transpose(gamma)
            mu = ((np.matmul(gamTranspose, self.points).T) / N_k).T

            x_minus_mu = self.points[np.newaxis, :] - mu[:, np.newaxis]

            gamma = gamTranspose[:, np.newaxis]

            x_minus_mu_t = np.transpose(x_minus_mu, axes=[0, 2, 1])

            mult = gamma * x_minus_mu_t

            #sigma: KxDxD numpy array, the diagonal standard deviation of each gaussian
            sigma = np.zeros((self.K, self.points.shape[1], self.points.shape[1]))

            #summation of K (for each center)
            for k in range(self.K):
                sigma[k] = np.matmul(mult[k], x_minus_mu[k])

            N_k_3D = (np.transpose(N_k))[:, np.newaxis, np.newaxis]

            sigma = sigma / N_k_3D

            sigma *= (np.eye(self.points.shape[1]))

            return pi, mu, sigma

            

    def __call__(self, full_matrix=FULL_MATRIX, abs_tol=1e-16, rel_tol=1e-16, **kwargs):  # No need to change
        """
        Args:
            abs_tol: convergence criteria w.r.t absolute change of loss
            rel_tol: convergence criteria w.r.t relative change of loss
            kwargs: any additional arguments you want

        Return:
            gamma(tau): NxK array, the posterior distribution (a.k.a, the soft cluster assignment) for each observation.
            (pi, mu, sigma): (1xK np array, KxD numpy array, KxDxD numpy array)

        Hint:
            You do not need to change it. For each iteration, we process E and M steps, then update the paramters.
        """
        pi, mu, sigma = self._init_components(**kwargs)
        pbar = tqdm(range(self.max_iters))

        for it in pbar:
            # E-step
            gamma = self._E_step(pi, mu, sigma, full_matrix)

            # M-step
            pi, mu, sigma = self._M_step(gamma, full_matrix)

            # calculate the negative log-likelihood of observation
            joint_ll = self._ll_joint(pi, mu, sigma, full_matrix)
            loss = -np.sum(self.logsumexp(joint_ll))
            if it:
                diff = np.abs(prev_loss - loss)
                if diff < abs_tol and diff / prev_loss < rel_tol:
                    break
            prev_loss = loss
            pbar.set_description('iter %d, loss: %.4f' % (it, loss))
        return gamma, (pi, mu, sigma)

