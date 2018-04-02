function pb5(n)

    A = zeros(n);

    for i = 1:rows(A)
        for j = 1:columns(A)
            if(i > j)
                A(i,j) = -1;
            end
            if(i == j)
                A(i,j) = 1;
            end

            A(i,columns(A)) = 1;
        end
    end

    b = zeros(n,1);

    for i = 1:size(b,1)
        b(i) = -i + 3;
    end

    disp(A);
    disp(b);

    x = lupsolve(A,b);
    printf('Rezolvare cu LUP');
    disp(x);
    
    x = pb1(n, A, b);
    printf('Rezolvare cu Gauss');
    disp(x);
end
