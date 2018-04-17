function pb3(n)
    A = rand(n,n);
    disp('Matrix A : ');
    disp(A);
    b = A*ones(n,1);
    disp('b : ');
    disp(b);

    % rezolvam cu Gauss ca sa verificam cu LUP  
    disp('Gauss : ');
    x = pb1(A,b);
    disp(x);

    % rezolvam cu LUP 
    disp('LUP : ');
    x = lupsolve(A,b);
    disp(x);
end
