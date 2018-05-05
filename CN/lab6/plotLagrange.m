function plotLagrange(f,lagNodes,range)
  vals = f(range);
  lagNodeVals = f(lagNodes);
  interpVals = lagInterp(lagNodes, lagNodeVals, range);
  
  hold;
  plot(range, vals);  % fa vloriile lui f
  plot(range, interpVals);  % valorile polinomului
end