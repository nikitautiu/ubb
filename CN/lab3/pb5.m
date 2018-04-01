function pb5(err)
    prevE = aproxE(1);
    currE = aproxE(2);
    ind = 3;
    while abs(prevE - currE) > err
        prevE = currE;
        currE = aproxE(ind);
        ind = ind + 1;
    end
    
    printf('e aproximat la %f\n', currE);
end
