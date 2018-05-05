function pbPract()
  % problemele practice

  % populatie
  vals = [1900 75.995
  1910 91.972
  1920 105.711
  1930 123.203
  1940 131.669
  1950 150.697
  1960 179.323
  1970 203.212
  1980 226.505
  1990 249.633
  2000 281.422] ;
  
  x = vals(:,1); y = vals(:, 2);
  disp("Poplatiile aproximate in 1995 si 2010");
  disp(lagInterp(x, y, [1975 2010]));
  
  % functia f
  x = [1 1.1 1.2 1.3 1.4];
  y = e(x);
  disp("Valoare func in 1.25");
  disp(lagInterp(x, y, [1.25]));
end