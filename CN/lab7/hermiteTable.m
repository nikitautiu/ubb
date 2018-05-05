function Q=hermiteTable(x, y, dy)
  % dandu-se x, f(x) si f'(x) sa se returneze tabela Q diferentelor divizate

  Q = zeros(length(x)*2, length(x)*2 + 1);
  Q(1:2:end, 1) = x;
  Q(2:2:end, 1) = x;
  Q(1:2:end, 2) = y;
  Q(2:2:end, 2) = y;
  Q(1:2:end, 3) = dy;
  
  % calculam restul de pe coloana 3
  for i = 1:rows(Q)-1
    if mod(i,2) == 0
       Q(i,3) = (y(round(i/2)+1)-y(round(i/2)))/ (x(round(i/2)+1)-x(round(i/2)));
    end
  end
  
  for j = 4:columns(Q)
    for i = 1:rows(Q) - j + 2
      Q(i,j) = (Q(i+1,j-1) - Q(i, j-1)) / (Q(i+j-2,1) - Q(i,1));
    end
  end
  

end