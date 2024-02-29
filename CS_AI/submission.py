# coding=utf-8
"""
This file is your main submission that will be graded against. Only copy-paste
code on the relevant classes included here. Do not add any classes or functions
to this file that are not part of the classes that we want.
"""

import heapq
import os
import pickle
import math


class PriorityQueue(object):
    """
    A queue structure where each element is served in order of priority.

    Elements in the queue are popped based on the priority with higher priority
    elements being served before lower priority elements.  If two elements have
    the same priority, they will be served in the order they were added to the
    queue.

    Traditionally priority queues are implemented with heaps, but there are any
    number of implementation options.

    (Hint: take a look at the module heapq)

    Attributes:
        queue (list): Nodes added to the priority queue.
    """

    def __init__(self):
        """Initialize a new Priority Queue."""

        self.queue = []


    def pop(self):
        """
        Pop top priority node from queue.

        Returns:
            The node with the highest priority.

            
        """
        try:
            old_node = heapq.heappop(self.queue)
            returnNode = (old_node[0],) + (old_node[2],)
            return returnNode
        except:
            raise IndexError

        # TODO: finish this function!
        

    def remove(self, node):
        """
        Remove a node from the queue.

        Hint: You might require this in ucs. However, you may
        choose not to use it or to define your own method.

        Args:
            node (tuple): The node to remove from the queue.
        """


        for i in range(0, len(self.queue)):
            if self.queue[i] == node[1]:
                self.queue[i] = ((-1, 'remove'))
                heapq.heapify(self.queue)
                heapq.heappop(self.queue)
                break

        
    def __iter__(self):
        """Queue iterator."""

        return iter(sorted(self.queue))

    def __str__(self):
        """Priority Queue to string."""

        return 'PQ:%s' % self.queue

    def append(self, node):
        """
        Append a node to the queue.

        Args:
            node: Comparable Object to be added to the priority queue.
        """
        

        count = 0
        for element in self.queue:
            if (node[0] == element[0]):
                count += 1
            elif (element[0] > node[0]):
                break
        counting = (count, )
        partone = (node[0],)
        parttwo = (node[1],)
        new_node = partone + counting + parttwo
        heapq.heappush(self.queue, new_node)


        # TODO: finish this function!
        
        
    def __contains__(self, key):
        """
        Containment Check operator for 'in'

        Args:
            key: The key to check for in the queue.

        Returns:
            True if key is found in queue, False otherwise.
        """

        return key in [n[-1] for n in self.queue]

    def __eq__(self, other):
        """
        Compare this Priority Queue with another Priority Queue.

        Args:
            other (PriorityQueue): Priority Queue to compare against.

        Returns:
            True if the two priority queues are equivalent.
        """

        return self.queue == other.queue

    def size(self):
        """
        Get the current size of the queue.

        Returns:
            Integer of number of items in queue.
        """

        return len(self.queue)

    def clear(self):
        """Reset queue to empty (no nodes)."""

        self.queue = []

    def top(self):
        """
        Get the top item in the queue.

        Returns:
            The first item stored in the queue.
        """

        return self.queue[0]


def breadth_first_search(graph, start, goal):
    """
    Warm-up exercise: Implement breadth-first-search.

    See README.md for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        start (str): Key for the start node.
        goal (str): Key for the end node.

    Returns:
        The best path as a list from the start and goal nodes (including both).
    """
    if (start == goal):
        return []
    frontier = PriorityQueue()
    frontier.append((0, start))
    reached = []
    backwardsPath = {}
    found = False
    depth = 0
    reached.append(start)
    while (frontier.size() != 0) and (not found):
        node = (frontier.pop())
        depth = node[0] + 1
        list = graph.neighbors(node[1])
        list = sorted(list)
        for neighbor in list:
            s = neighbor
            if s == goal:
                backwardsPath[s] = node[1]
                reached.append(s)
                found = True
                break               
            if s not in reached:
                backwardsPath[s] = node[1]
                reached.append(s)
                frontier.append((depth, s))
        if goal in reached:
            break
                
    
    
    solution = []
    lastNode = goal
    solution.insert(0, goal)
    while lastNode != start:
        temp = backwardsPath[lastNode]
        solution.insert(0,temp)
        lastNode = temp
    
    return solution
    
    
    """
    if (start == goal):
        return []
    frontier = PriorityQueue()
    frontier.append((0, start))
    answer = []
    while (frontier.size != 0):
        node = frontier.pop()
        answer.append(node)

        #optimzation from canvas 
        if (node == goal):
            break
        
        neighborList = graph.neighbors(node[1])
        neighborList.sort()
        
        for element in neighborList:
            if (not (frontier.__contains__(element))):
                frontier.append(element)
    

    return answer
    """
    # TODO: finish this function!
    raise NotImplementedError


def uniform_cost_search(graph, start, goal):
    """
    Warm-up exercise: Implement uniform_cost_search.

    See README.md for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        start (str): Key for the start node.
        goal (str): Key for the end node.

    Returns:
        The best path as a list from the start and goal nodes (including both).
    """
    if (start == goal):
        return []
    node = (0, start)
    frontier = PriorityQueue()
    frontier.append(node)
    explored = {}
    reached = {}
    VS = []
    
    found = False
    reached[start] = 0
    explored[start] = 0
    while (frontier.size() != 0 and (not found)):
        node = frontier.pop()
        if (node in VS):
            continue
        else:
            if goal == node[1]:
                found = True
                break
            else:
                VS.append(node[1])
                for element in (graph.neighbors(node[1])):
                    edge = graph.get_edge_weight(node[1], element)
                    cost = (edge + node[0])
                    if element not in frontier and element not in explored:
                        explored[element] = node[1]
                        frontier.append((cost, element))
                        reached[element] = cost
                    elif ((reached[element]) > cost):
                        frontier.remove((reached[element], element))
                        frontier.append((cost, element))
                        reached[element] = cost
                        explored[element] = node[1]    



    solution = []
    lastNode = goal
    solution.insert(0, goal)
    while lastNode != start:
        temp = explored[lastNode]
        solution.insert(0, temp)
        lastNode = temp
    return solution
    
    # TODO: finish this function!
    raise NotImplementedError


def null_heuristic(graph, v, goal):
    """
    Null heuristic used as a base line.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        v (str): Key for the node to calculate from.
        goal (str): Key for the end node to calculate to.

    Returns:
        0
    """

    return 0


def euclidean_dist_heuristic(graph, v, goal):
    """
    Warm-up exercise: Implement the euclidean distance heuristic.

    See README.md for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        v (str): Key for the node to calculate from.
        goal (str): Key for the end node to calculate to.

    Returns:
        Euclidean distance between `v` node and `goal` node
    """
    answerCords = graph.nodes[goal]['pos']
    currCords = graph.nodes[v]['pos']
    distance = math.sqrt((((currCords[1]) - (answerCords[1])) ** 2) + (((currCords[0]) - (answerCords[0])) ** 2))
    return distance
    # TODO: finish this function!
    raise NotImplementedError


def a_star(graph, start, goal, heuristic=euclidean_dist_heuristic):
    """
    Warm-up exercise: Implement A* algorithm.

    See README.md for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        start (str): Key for the start node.
        goal (str): Key for the end node.
        heuristic: Function to determine distance heuristic.
            Default: euclidean_dist_heuristic.

    Returns:
        The best path as a list from the start and goal nodes (including both).
    """
    if (start == goal):
        return []
    node = (0, start)
    frontier = PriorityQueue()
    frontier.append(node)
    explored = {}
    reached = {}
    VS = []
    found = False
    reached[start] = 0
    explored[start] = 0
    while (frontier.size() != 0 and (not found)):
        node = frontier.pop()
        if (node in VS):
            continue
        else:
            if goal == node[1]:
                found = True
                break
            else :
                for element in graph.neighbors(node[1]):
                    edge = graph.get_edge_weight(node[1], element)
                    endDist = euclidean_dist_heuristic(graph, element, goal)
                    startDist = (edge + reached[node[1]])
                    if element not in frontier and element not in explored:
                        explored[element] = node[1]
                        frontier.append(((startDist + endDist), element))   
                        reached[element] = startDist
                    elif ((reached[element]) > startDist):
                        frontier.remove((reached[element], element))
                        frontier.append((startDist, element))
                        reached[element] = startDist
                        explored[element] = node[1]        


    solution = []
    lastNode = goal
    solution.insert(0, goal)
    while lastNode != start:
        temp = explored[lastNode]
        solution.insert(0,temp)
        lastNode = temp
    
    return solution
    # TODO: finish this function!
    raise NotImplementedError


def bidirectional_ucs(graph, start, goal):
    """
    Exercise 1: Bidirectional Search.

    See README.md for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        start (str): Key for the start node.
        goal (str): Key for the end node.

    Returns:
        The best path as a list from the start and goal nodes (including both)
    
    if (start == goal):
        return []
    frontStart = PriorityQueue()
    frontEnd = PriorityQueue()
    frontStart.append((0, start))
    frontEnd.append((0, goal))
    exA = {}
    exB = {}
    reached = {}
    found = False
    reached[start] = 0
    exA[start] = 0
    exB[goal] = 0
    solution = []
    sol_val = 0
    while ((frontStart.size() != 0 or frontEnd.size() != 0)):
        one = frontStart.pop()
        two = frontEnd.pop()
        if (one in exB):
            temp_solution = []
            tempVal = one[0] + two[0]
            n = goal
            temp_solution.insert(0, n)
            while n != one[1]:
                temp = exB[n]
                temp_solution.insert(0, temp)
                n = temp
            s = exA[one[1]]
            while s != start:
                temp = exA[s]
                temp_solution.insert(0, temp)
                s = temp
            
        if (two in exA):
            temp_solution = []
            tempVal = one[0] + two[0]
            s = start
            temp_solution.insert(0, s)
            while s != two[1]:
                temp = exA[s]
                temp_solution.append(temp)
                s = temp
            n = exB[two[1]]
            while n != one[1]:
                temp = exB[n]
                temp_solution.append(temp)
                n = temp
            

        for element in graph.neighbors(one):
            edge = graph.get_edge_weight(one[1], element)
            cost = (edge + one[0])
            calc = 0
            
            if element not in exA:
                exA[element] = one[1]
                frontStart.append((cost, element))
                reached[element] = cost
            elif ((reached[element]) > cost):
                frontStart.append((cost, element))
                reached[element] = cost
                exA[element] = one[1]  
        
        for element in graph.neighbors(two):
            edge = graph.get_edge_weight(two[1], element)
            cost = (edge + two[0])
            if element not in exB:
                exB[element] = two[1]
                frontEnd.append((cost, element))
                reached[element] = cost
            elif ((reached[element]) > cost):
                frontEnd.append((cost, element))
                reached[element] = cost
                exB[element] = two[1]  
    """
    if (start == goal):
        return []
    node = (0, start)
    frontier = PriorityQueue()
    frontier.append(node)
    explored = {}
    reached = {}
    VS = []
    
    found = False
    reached[start] = 0
    explored[start] = 0
    while (frontier.size() != 0 and (not found)):
        node = frontier.pop()
        if (node in VS):
            continue
        else:
            if goal == node[1]:
                found = True
                break
            else:
                VS.append(node[1])
                for element in (graph.neighbors(node[1])):
                    edge = graph.get_edge_weight(node[1], element)
                    cost = (edge + node[0])
                    if element not in frontier and element not in explored:
                        explored[element] = node[1]
                        frontier.append((cost, element))
                        reached[element] = cost
                    elif ((reached[element]) > cost):
                        frontier.remove((reached[element], element))
                        frontier.append((cost, element))
                        reached[element] = cost
                        explored[element] = node[1]    



    solution = []
    lastNode = goal
    solution.insert(0, goal)
    while lastNode != start:
        temp = explored[lastNode]
        solution.insert(0, temp)
        lastNode = temp
    return solution
    
    
    

    # TODO: finish this function!
    


def bidirectional_a_star(graph, start, goal,
                         heuristic=euclidean_dist_heuristic):
    """
    Exercise 2: Bidirectional A*.

    See README.md for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        start (str): Key for the start node.
        goal (str): Key for the end node.
        heuristic: Function to determine distance heuristic.
            Default: euclidean_dist_heuristic.

    Returns:
        The best path as a list from the start and goal nodes (including both).
    """

    if (start == goal):
        return []
    node = (0, start)
    frontier = PriorityQueue()
    frontier.append(node)
    explored = {}
    reached = {}
    VS = []
    found = False
    reached[start] = 0
    explored[start] = 0
    while (frontier.size() != 0 and (not found)):
        node = frontier.pop()
        if (node in VS):
            continue
        else:
            if goal == node[1]:
                found = True
                break
            else:
                for element in graph.neighbors(node[1]):
                    edge = graph.get_edge_weight(node[1], element)
                    endDist = euclidean_dist_heuristic(graph, element, goal)
                    startDist = (edge + reached[node[1]])
                    if element not in frontier and element not in explored:
                        explored[element] = node[1]
                        frontier.append(((startDist + endDist), element))   
                        reached[element] = startDist
                    elif ((reached[element]) > startDist):
                        frontier.remove((reached[element], element))
                        frontier.append((startDist, element))
                        reached[element] = startDist
                        explored[element] = node[1]        


    solution = []
    lastNode = goal
    solution.insert(0, goal)
    while lastNode != start:
        temp = explored[lastNode]
        solution.insert(0,temp)
        lastNode = temp
    
    return solution
    # TODO: finish this function!
    


def tridirectional_search(graph, goals):
    """
    Exercise 3: Tridirectional UCS Search

    See README.MD for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        goals (list): Key values for the 3 goals

    Returns:
        The best path as a list from one of the goal nodes (including both of
        the other goal nodes).
    """
    if (goals[0] == goals[1] == goals[2]):
        return []
    # TODO: finish this function
    return []


def tridirectional_upgraded(graph, goals, heuristic=euclidean_dist_heuristic, landmarks=None):
    """
    Exercise 4: Upgraded Tridirectional Search

    See README.MD for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        goals (list): Key values for the 3 goals
        heuristic: Function to determine distance heuristic.
            Default: euclidean_dist_heuristic.
        landmarks: Iterable containing landmarks pre-computed in compute_landmarks()
            Default: None

    Returns:
        The best path as a list from one of the goal nodes (including both of
        the other goal nodes).
    """
    if (goals[0] == goals[1] == goals[2]):
        return []
    # TODO: finish this function
    return []
    


def return_your_name():
    """Return your name from this function"""
    return ("Jadon Co")
    # TODO: finish this function
    raise NotImplementedError


def compute_landmarks(graph):
    """
    Feel free to implement this method for computing landmarks. We will call
    tridirectional_upgraded() with the object returned from this function.

    Args:
        graph (ExplorableGraph): Undirected graph to search.

    Returns:
    List with not more than 4 computed landmarks. 
    """
    return None


def custom_heuristic(graph, v, goal):
    """
       Feel free to use this method to try and work with different heuristics and come up with a better search algorithm.
       Args:
           graph (ExplorableGraph): Undirected graph to search.
           v (str): Key for the node to calculate from.
           goal (str): Key for the end node to calculate to.
       Returns:
           Custom heuristic distance between `v` node and `goal` node
       """
    pass


# Extra Credit: Your best search method for the race
def custom_search(graph, start, goal, data=None):
    """
    Race!: Implement your best search algorithm here to compete against the
    other student agents.

    If you implement this function and submit your code to Gradescope, you'll be
    registered for the Race!

    See README.md for exercise description.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        start (str): Key for the start node.
        goal (str): Key for the end node.
        data :  Data used in the custom search.
            Will be passed your data from load_data(graph).
            Default: None.

    Returns:
        The best path as a list from the start and goal nodes (including both).
    """

    # TODO: finish this function!
    raise NotImplementedError


def load_data(graph, time_left):
    """
    Feel free to implement this method. We'll call it only once 
    at the beginning of the Race, and we'll pass the output to your custom_search function.
    graph: a networkx graph
    time_left: function you can call to keep track of your remaining time.
        usage: time_left() returns the time left in milliseconds.
        the max time will be 10 minutes.

    * To get a list of nodes, use graph.nodes()
    * To get node neighbors, use graph.neighbors(node)
    * To get edge weight, use graph.get_edge_weight(node1, node2)
    """

    # nodes = graph.nodes()
    return None
 
 
def haversine_dist_heuristic(graph, v, goal):
    """
    Note: This provided heuristic is for the Atlanta race.

    Args:
        graph (ExplorableGraph): Undirected graph to search.
        v (str): Key for the node to calculate from.
        goal (str): Key for the end node to calculate to.

    Returns:
        Haversine distance between `v` node and `goal` node
    """

    #Load latitude and longitude coordinates in radians:
    vLatLong = (math.radians(graph.nodes[v]["pos"][0]), math.radians(graph.nodes[v]["pos"][1]))
    goalLatLong = (math.radians(graph.nodes[goal]["pos"][0]), math.radians(graph.nodes[goal]["pos"][1]))

    #Now we want to execute portions of the formula:
    constOutFront = 2*6371 #Radius of Earth is 6,371 kilometers
    term1InSqrt = (math.sin((goalLatLong[0]-vLatLong[0])/2))**2 #First term inside sqrt
    term2InSqrt = math.cos(vLatLong[0])*math.cos(goalLatLong[0])*((math.sin((goalLatLong[1]-vLatLong[1])/2))**2) #Second term
    return constOutFront*math.asin(math.sqrt(term1InSqrt+term2InSqrt)) #Straight application of formula
