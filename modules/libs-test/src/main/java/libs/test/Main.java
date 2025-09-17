package libs.test;

import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        // Objective function: 3x + 5y
        LinearObjectiveFunction objective = new LinearObjectiveFunction(new double[] {3, 5}, 0);

        // Constraints
        Collection<LinearConstraint> constraints = new ArrayList<>();
        constraints.add(new LinearConstraint(new double[] {2, 8}, Relationship.LEQ, 13));
        constraints.add(new LinearConstraint(new double[] {5, -1}, Relationship.LEQ, 11));
        constraints.add(new LinearConstraint(new double[] {1, 0}, Relationship.GEQ, 0));
        constraints.add(new LinearConstraint(new double[] {0, 1}, Relationship.GEQ, 0));

        // Solver
        SimplexSolver solver = new SimplexSolver();
        PointValuePair solution = solver.optimize(
                new MaxIter(100),
                objective,
                new LinearConstraintSet(constraints),
                GoalType.MAXIMIZE,
                new NonNegativeConstraint(true)
        );

        // Print results
        double x = solution.getPoint()[0];
        double y = solution.getPoint()[1];
        double value = solution.getValue();

        System.out.println("Optimal Solution:");
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("Max Value = " + value);
    }
}