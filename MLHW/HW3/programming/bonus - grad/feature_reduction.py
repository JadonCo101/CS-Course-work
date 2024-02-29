import pandas as pd
import statsmodels.api as sm
from typing import List


class FeatureReduction(object):
    def __init__(self):
        pass

    @staticmethod
    def forward_selection(
        data: pd.DataFrame, target: pd.Series, significance_level: float = 0.05
    ) -> List[str]:  # 9 pts
        """
        Args:
            data: (pandas data frame) contains the feature matrix
            target: (pandas series) represents target feature to search to generate significant features
            significance_level: (float) threshold to reject the null hypothesis
        Return:
            forward_list: (python list) contains significant features. Each feature
            name is a string
        """

        
        f_list = []

        while len(list(data.columns)) > 0:
            remaining = list(set(list(data.columns)) - set(f_list))
            p_vals = []
    
    
            for f in remaining:
                X = sm.add_constant((data[f_list + [f]]))
                model = sm.OLS(target, X).fit()
                p = model.pvalues[f]
                p_vals.append((f, p))

            best, min_p = min(p_vals, key=lambda x: x[1])

            if min_p < significance_level:
                f_list.append(best)
            else:
                break

        return f_list

    @staticmethod
    def backward_elimination(
        data: pd.DataFrame, target: pd.Series, significance_level: float = 0.05
    ) -> List[str]:  # 9 pts
        """
        Args:
            data: (pandas data frame) contains the feature matrix
            target: (pandas series) represents target feature to search to generate significant features
            significance_level: (float) threshold to reject the null hypothesis
        Return:
            backward_list: (python list) contains significant features. Each feature
            name is a string
        """

        
        b_list = list(data.columns).copy()

        while len(b_list) > 0:
            X = sm.add_constant(data[b_list])
            model = sm.OLS(target, X).fit()
            max_p = max(model.pvalues[1:], default=0)

            if max_p < significance_level:
                break
            else:
                f_remove = model.pvalues.idxmax()
                b_list.remove(f_remove)

        return b_list
