function xx=fundamentalL(m,nodes,x)
  % returneaza valorile polinoamelor fundamentale
  % pt un grad dat m si niste noduri intr-un punct x
  
  nominator = zeros(1, m);
  denom = zeros(1, m);
  for i = 0:m
    nominator(i+1) = prod(x .- [nodes(1:(i)), nodes((i+2):end)]);
  end
  for i = 0:m
    denom(i+1) = prod(nodes(i+1) .- [nodes(1:(i)), nodes((i+2):end)]);
  end
  
  xx = nominator ./ denom;
end