import sys

'''
WRITE YOUR CODE BELOW.
'''
import numpy
from numpy import zeros, float32
#  pgmpy͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
import pgmpy
import random
from pgmpy.models import BayesianModel
from pgmpy.factors.discrete import TabularCPD
from pgmpy.inference import VariableElimination
#You are not allowed to use following set of modules from 'pgmpy' Library.͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
#͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
# pgmpy.sampling.*͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
# pgmpy.factors.*͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
# pgmpy.estimators.*͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉

def make_security_system_net():
    """Create a Bayes Net representation of the above security system problem. 
    Use the following as the name attribute: "H","C", "M","B", "Q", 'K",
    "D"'. (for the tests to work.)
    """
    BayesNet = BayesianModel()
    BayesNet.add_node("H")
    BayesNet.add_node("C")
    BayesNet.add_node("M")
    BayesNet.add_node("B")
    BayesNet.add_node("Q")
    BayesNet.add_node("K")
    BayesNet.add_node("D")

    BayesNet.add_edge("C","Q")
    BayesNet.add_edge("H","Q")
    BayesNet.add_edge("M","K")
    BayesNet.add_edge("B","K")
    BayesNet.add_edge("Q","D")
    BayesNet.add_edge("K","D")
    
    return BayesNet


def set_probability(bayes_net):
    """Set probability distribution for each node in the security system.
    Use the following as the name attribute: "H","C", "M","B", "Q", 'K",
    "D"'. (for the tests to work.)
    """
    # TODO: set the probability distribution for each node͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
    cpd_h = TabularCPD('H', 2, values=[[0.5], [0.5]])
    cpd_c = TabularCPD('C', 2, values=[[0.7], [0.3]])
    cpd_m = TabularCPD('M', 2, values=[[0.2], [0.8]])
    cpd_b = TabularCPD('B', 2, values=[[0.5], [0.5]])
    
    cpd_qch = TabularCPD('Q', 2, values=[[0.95, 0.45, 0.75, 0.10], \
                    [0.05, 0.55, 0.25, 0.90]], evidence=['C', 'H'], evidence_card=[2, 2])
    cpd_kbm = TabularCPD('K', 2, values=[[0.25, 0.05, 0.99, 0.85], \
                    [0.75, 0.95, 0.01, 0.15]], evidence=['B', 'M'], evidence_card=[2, 2])
    cpd_dkq = TabularCPD('D', 2, values=[[0.98, 0.4, 0.65, 0.01], \
                    [0.02, 0.6, 0.35, 0.99]], evidence=['K', 'Q'], evidence_card=[2, 2])

    bayes_net.add_cpds(cpd_h, cpd_c, cpd_m, cpd_b, cpd_qch, cpd_kbm, cpd_dkq)
    return bayes_net


def get_marginal_double0(bayes_net):
    """Calculate the marginal probability that Double-0 gets compromised.
    """
    # TODO: finish this function͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉

    solver = VariableElimination(bayes_net)
    marginal_prob = solver.query(variables=['D'], joint=False)
    double0_prob = marginal_prob['D'].values
    return double0_prob[1]


def get_conditional_double0_given_no_contra(bayes_net):
    """Calculate the conditional probability that Double-0 gets compromised
    given Contra is shut down.
    """
    # TODO: finish this function͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
    solver = VariableElimination(bayes_net)
    conditional_prob = solver.query(variables=['D'],evidence={'C':0}, joint=False)
    double0_prob = conditional_prob['D'].values
    return double0_prob[1]


def get_conditional_double0_given_no_contra_and_bond_guarding(bayes_net):
    """Calculate the conditional probability that Double-0 gets compromised
    given Contra is shut down and Bond is reassigned to protect M.
    """
    # TODO: finish this function͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
    solver = VariableElimination(bayes_net)
    conditional_prob = solver.query(variables=['D'],evidence={'C':0,'B':1}, joint=False)
    double0_prob = conditional_prob['D'].values
    return double0_prob[1]


def get_game_network():
    """Create a Bayes Net representation of the game problem.
    Name the nodes as "A","B","C","AvB","BvC" and "CvA".  """
    BayesNet = BayesianModel()
    # TODO: fill this out͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
    BayesNet.add_node("A")
    BayesNet.add_node("B")
    BayesNet.add_node("C")
    BayesNet.add_node("AvB")
    BayesNet.add_node("BvC")
    BayesNet.add_node("CvA")
    

    BayesNet.add_edge("A","AvB")
    BayesNet.add_edge("B","AvB")
    BayesNet.add_edge("B","BvC")
    BayesNet.add_edge("C","BvC")
    BayesNet.add_edge("C","CvA")
    BayesNet.add_edge("A","CvA")


    cpd_a = TabularCPD('A', 4, values=[[0.15], [0.45], [0.30], [0.10]])
    cpd_b = TabularCPD('B', 4, values=[[0.15], [0.45], [0.30], [0.10]])
    cpd_c = TabularCPD('C', 4, values=[[0.15], [0.45], [0.30], [0.10]])
    
    cpd_avb = TabularCPD('AvB', 3, [[0.1, 0.2, 0.15, 0.05, 0.6, 0.1, 0.20, 0.15, 0.75, 0.6, 0.1, 0.2, 0.9, 0.75, 0.6, 0.1], [0.1, 0.6, 0.75, 0.9, 0.2, 0.1, 0.6, 0.75, 0.15, 0.2, 0.1, 0.6, 0.05, 0.15, 0.2, 0.1], [0.8, 0.2, 0.10, 0.05, 0.2, 0.8, 0.2, 0.1, 0.1, 0.2, 0.8, 0.2, 0.05, 0.1, 0.2, 0.8]], evidence=['A','B'], evidence_card=[4,4])
    cpd_bvc = TabularCPD('BvC', 3, [[0.1, 0.2, 0.15, 0.05, 0.6, 0.1, 0.20, 0.15, 0.75, 0.6, 0.1, 0.2, 0.9, 0.75, 0.6, 0.1], [0.1, 0.6, 0.75, 0.9, 0.2, 0.1, 0.6, 0.75, 0.15, 0.2, 0.1, 0.6, 0.05, 0.15, 0.2, 0.1], [0.8, 0.2, 0.10, 0.05, 0.2, 0.8, 0.2, 0.1, 0.1, 0.2, 0.8, 0.2, 0.05, 0.1, 0.2, 0.8]], evidence=['B','C'], evidence_card=[4,4])
    cpd_cva = TabularCPD('CvA', 3, [[0.1, 0.2, 0.15, 0.05, 0.6, 0.1, 0.20, 0.15, 0.75, 0.6, 0.1, 0.2, 0.9, 0.75, 0.6, 0.1], [0.1, 0.6, 0.75, 0.9, 0.2, 0.1, 0.6, 0.75, 0.15, 0.2, 0.1, 0.6, 0.05, 0.15, 0.2, 0.1], [0.8, 0.2, 0.10, 0.05, 0.2, 0.8, 0.2, 0.1, 0.1, 0.2, 0.8, 0.2, 0.05, 0.1, 0.2, 0.8]], evidence=['C','A'], evidence_card=[4,4])


    BayesNet.add_cpds(cpd_a, cpd_b, cpd_c, cpd_avb, cpd_bvc, cpd_cva)

    return BayesNet


def calculate_posterior(bayes_net):
    """Calculate the posterior distribution of the BvC match given that A won against B and tied C. 
    Return a list of probabilities corresponding to win, loss and tie likelihood."""
    posterior = [0,0,0]
    # TODO: finish this function͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉 
    solver = VariableElimination(bayes_net)
    conditional_prob = solver.query(variables=['BvC'],evidence={'AvB':0,'CvA':2}, joint=False)
    posterior = conditional_prob['BvC'].values

    return posterior # list 


def getA(bayes_net, evidence):
    """
    ***DO NOT POST OR SHARE THESE FUNCTIONS WITH ANYONE***
    Returns a distribution of probability of skill levels for team "A" given an evidence vector.
    Parameter: 
    : bayes net: Baysian Model Object
    : evidence: array of length 6 containing the skill levels for teams A, B, C in indices 0, 1, 2
    : and match outcome for AvB, BvC and CvA in indices 3, 4 and 5
    """
    A_cpd = bayes_net.get_cpds("A")      
    AvB_cpd = bayes_net.get_cpds("AvB")
    match_table = AvB_cpd.values
    team_table = A_cpd.values
    a = []
    normalizer = 0
    for i in range(4):
        normalizer += (team_table[i] * match_table[evidence[3]][i][evidence[1]] * 
                       match_table[evidence[5]][i][evidence[2]])
    for i in range(4):
        unnorm_prob = (team_table[i] * match_table[evidence[3]][i][evidence[1]] * 
                       match_table[evidence[5]][i][evidence[2]])
        a.append(unnorm_prob)
    return numpy.array(a)/normalizer


def getBvC(bayes_net, B, C):
    """
    ***DO NOT POST OR SHARE THESE FUNCTIONS WITH ANYONE***
    Returns a distribution of probability of match outcomes for "BvC" given the skill levels of B and C as evidence
    Parameter: 
    : bayes net: Baysian Model Object
    : B: int representing team B's skill level
    : C: int representing team C's skill level
    """
    BvC_cpd = bayes_net.get_cpds('BvC')
    match_table = BvC_cpd.values
    bvc = []
    for i in range(0, 3):
        bvc.append(match_table[i][B][C])
    return bvc   


def getB(bayes_net, evidence):
    """
    ***DO NOT POST OR SHARE THESE FUNCTIONS WITH ANYONE***
    Returns a distribution of probability of skill levels for team "B" given an evidence vector.
    Parameter: 
    : bayes net: Baysian Model Object
    : evidence: array of length 6 containing the skill levels for teams A, B, C in indices 0, 1, 2
    : and match outcome for AvB, BvC and CvA in indices 3, 4 and 5
    """
    B_cpd = bayes_net.get_cpds("B")      
    AvB_cpd = bayes_net.get_cpds("AvB")
    match_table = AvB_cpd.values
    team_table = B_cpd.values
    b = []
    normalizer = 0
    for i in range(4):
        normalizer += (team_table[i] * match_table[evidence[3]][evidence[0]][i] * 
                       match_table[evidence[4]][i][evidence[2]])
    for i in range(4):
        unnorm_prob = (team_table[i] * match_table[evidence[3]][evidence[0]][i] * 
                       match_table[evidence[4]][i][evidence[2]])
        b.append(unnorm_prob)
    return numpy.array(b)/normalizer


def getC(bayes_net, evidence):
    """
    ***DO NOT POST OR SHARE THESE FUNCTIONS WITH ANYONE***
    Returns a distribution of probability of skill levels for team "C" given an evidence vector.
    Parameter: 
    : bayes net: Baysian Model Object
    : evidence: array of length 6 containing the skill levels for teams A, B, C in indices 0, 1, 2
    : and match outcome for AvB, BvC and CvA in indices 3, 4 and 5
    """
    C_cpd = bayes_net.get_cpds("C")      
    AvB_cpd = bayes_net.get_cpds("AvB")
    match_table = AvB_cpd.values
    team_table = C_cpd.values
    c = []
    normalizer = 0
    for i in range(4):
        normalizer += (team_table[i] * match_table[evidence[5]][i][evidence[0]] * 
                       match_table[evidence[4]][evidence[1]][i])
    for i in range(4):
        unnorm_prob = (team_table[i] * match_table[evidence[5]][i][evidence[0]] * 
                       match_table[evidence[4]][evidence[1]][i])
        c.append(unnorm_prob)
    return numpy.array(c)/normalizer

def calculateMH(bayes_net, evidence):
    """
    ***DO NOT POST OR SHARE THESE FUNCTIONS WITH ANYONE***
    Returns the probability of a state.
    Parameter: 
    : bayes net: Baysian Model Object
    : evidence: array of length 6 containing the skill levels for teams A, B, C in indices 0, 1, 2
    : and match outcome for AvB, BvC and CvA in indices 3, 4 and 5
    """
    AvB_cpd = bayes_net.get_cpds('AvB').values
    BvC_cpd = bayes_net.get_cpds('BvC').values
    CvA_cpd = bayes_net.get_cpds('CvA').values
    skill_dist = [0.15, 0.45, 0.30, 0.10]
    A_skill_prob = skill_dist[evidence[0]]
    B_skill_prob = skill_dist[evidence[1]]
    C_skill_prob = skill_dist[evidence[2]]
    AvB_outcome_prob = AvB_cpd[evidence[3]][evidence[0]][evidence[1]]
    BvC_outcome_prob = BvC_cpd[evidence[4]][evidence[1]][evidence[2]]
    CvA_outcome_prob = CvA_cpd[evidence[5]][evidence[2]][evidence[0]]
    
    
    return (A_skill_prob * B_skill_prob * C_skill_prob * AvB_outcome_prob * 
            BvC_outcome_prob * CvA_outcome_prob)


def Gibbs_sampler(bayes_net, initial_state):
    """Complete a single iteration of the Gibbs sampling algorithm 
    given a Bayesian network and an initial state value. 
    
    initial_state is a list of length 6 where: 
    index 0-2: represent skills of teams A,B,C (values lie in [0,3] inclusive)
    index 3-5: represent results of matches AvB, BvC, CvA (values lie in [0,2] inclusive)
    
    Returns the new state sampled from the probability distribution as a tuple of length 6.
    Return the sample as a tuple.    
    """
    sample = tuple(initial_state)    
    if not sample:
        sample = ((random.randint(0,3)), (random.randint(0,3)), (random.randint(0,3)), (random.randint(0,2)), (random.randint(0,2)), (random.randint(0,2)))
    # TODO: finish this function͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉

    

    choice = random.randint(0,3)
    if choice == 0:
        #changing A
        A_cpd = getA(bayes_net, sample)
        temp = numpy.random.choice((numpy.arange(4)), p=A_cpd)
        new_samp = list(sample)
        new_samp[0] = temp
        reVal = tuple(new_samp)
        return reVal
        #BayesNet.add_cpds(cpd_a, cpd_b, cpd_c, cpd_avb, cpd_bvc, cpd_cva)
    elif choice == 1:
        #changing B
        B_cpd = getB(bayes_net, sample)
        temp = numpy.random.choice((numpy.arange(4)), p=B_cpd)
        new_samp = list(sample)
        new_samp[1] = temp
        reVal = tuple(new_samp)
        return reVal
    elif choice == 2:
        #changing C
        C_cpd = getC(bayes_net, sample)
        temp = numpy.random.choice((numpy.arange(4)), p=C_cpd)
        new_samp = list(sample)
        new_samp[2] = temp
        reVal = tuple(new_samp)
        return reVal
    else:
        #changing BvC
        BvC_cpd = getBvC(bayes_net, sample[1],sample[2])
        temp = numpy.random.choice((numpy.arange(3)), p=BvC_cpd)
        new_samp = list(sample)
        new_samp[4] = temp
        reVal = tuple(new_samp)
        return reVal



def MH_sampler(bayes_net, initial_state):
    """Complete a single iteration of the MH sampling algorithm given a Bayesian network and an initial state value. 
    initial_state is a list of length 6 where: 
    index 0-2: represent skills of teams A,B,C (values lie in [0,3] inclusive)
    index 3-5: represent results of matches AvB, BvC, CvA (values lie in [0,2] inclusive)    
    Returns the new state sampled from the probability distribution as a tuple of length 6. 
    """

    sample = tuple(initial_state)   
    if not sample:
        AvB_Constant = (random.randint(0,2))
        CvA_Constant = (random.randint(0,2))
        sample = ((random.randint(0,3)), (random.randint(0,3)), (random.randint(0,3)), AvB_Constant, (random.randint(0,2)), CvA_Constant)
    else:
        AvB_Constant = initial_state[3]
        CvA_Constant = initial_state[5]
    # TODO: finish this function͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
    
    sample_two = ((random.randint(0,3)), (random.randint(0,3)), (random.randint(0,3)), AvB_Constant, (random.randint(0,2)), CvA_Constant) 

    first_iteration = tuple(sample_two)
    
    
    r = calculateMH(bayes_net, sample)
    u = calculateMH(bayes_net, first_iteration)
    if (u <= r):
        return first_iteration
    return sample


def compare_sampling(bayes_net, initial_state):
    """Compare Gibbs and Metropolis-Hastings sampling by calculating how long it takes for each method to converge."""    
    Gibbs_count = 0
    MH_count = 0
    MH_rejection_count = 0
    Gibbs_convergence = [0,0,0] # posterior distribution of the BvC match as produced by Gibbs 
    MH_convergence = [0,0,0] # posterior distribution of the BvC match as produced by MH
    # TODO: finish this function͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
    raise NotImplementedError        
    return Gibbs_convergence, MH_convergence, Gibbs_count, MH_count, MH_rejection_count


def sampling_question():
    """Question about sampling performance."""
    # TODO: assign value to choice and factor͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
    raise NotImplementedError
    choice = 2
    options = ['Gibbs','Metropolis-Hastings']
    factor = 0
    return options[choice], factor


def return_your_name():
    """Return your name from this function"""
    # TODO: finish this function͏︆͏󠄃͏󠄌͏󠄍͏󠄂͏️͏󠄄͏︂͏󠄉
    return "Jadon Co"
