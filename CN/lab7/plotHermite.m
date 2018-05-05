function plotHermite(f, df, nodes, range)
  % afiseaza f si polinomul sau Hermite, dandu-se f, derivata sa, nodurile si 
  % intervalul de afisare
  vals = f(range);
  herNodeVals = f(nodes);
  herDerivVals = df(nodes);
  interpVals = intHermite(nodes, herNodeVals, herDerivVals, range);
  
  hold;
  plot(range, vals);  % fa vloriile lui f
  plot(range, interpVals);  % valorile polinomului
end