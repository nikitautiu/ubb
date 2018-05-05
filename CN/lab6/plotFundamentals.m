function ax=plotFundamentals(nodes,range)
  % plot funadamental polynomials on a given range
  m = length(nodes) - 1;
  values = zeros(length(range), m+1); % hold the values here
  
  for ind=1:length(range)
    values(ind,:) = fundamentalL(m,nodes,range(ind));
  end

  % each col represents a polynomial
  hold
  for i=1:columns(values)
    plot(range, values(:,i));
  end
end