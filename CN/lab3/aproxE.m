function res=aproxE(n)
    E = 1 / (2 * (n+1));
    for i=n:-1:2
        E = (1 - E) / i; 
    end
    res = 1 / E;
end
