function pb3(n)

    % generaza sistemul din problema
    % termenii liberi
    b1 = ones(1,n) * 3;
    b1(1) = 4;
    b1(n) = 4;
    b1 = b1';
    
    % coeficientii
    A1 = zeros(n,n);
    for i=1:n
        A1(i,i) = 5;
        if i>1
            A1(i,i-1) = -1;
        end
        if i<n
            A1(i,i+1) = -1;
        end
    end
    
    % afiseaza matricile
    disp("MATRICEA SISTEMULUI");
    disp([A1, b1]);
    
    % acum genereaza coeficientii 2
    A2 = zeros(n,n);
    for i=1:n
        A2(i,i) = 5;
        if i>1
            A2(i,i-1) = -1;
        end
        if i<n
            A2(i,i+1) = -1;
        end
        if i>3
            A2(i,i-3) = -1;
        end
        if i<n-2
            A2(i,i+3) = -1;
        end
    end
    
    % termeni liberi 2
    b2 = ones(1,n);
    b2(1) = 3;
    b2(n) = 3;
    b2(2) = 2;
    b2(3) = 2;
    b2(n-1) = 2;
    b2(n-2) = 2;
    b2 = b2';

    disp("SOLUTII");    
    % afiseaza solutiile pt coeficientii si termenii liberi dati
    % se dau - x initial, eroarile si nr de iteratii
    x = jacobi(A1, b1, ones(1,n)', 0.001, 0.001, 100);
    disp('Jacobi: ');
    disp(x);
    x = sor(A1,b1, ones(1,n)', 1.234, 0.001, 0.001, 100);
    disp('SOR: ');
    disp(x);
    x = gaussSeidal(A1,b1,ones(1,n)',0.001,0.001,100);
    disp('Gauss Seidal: ');
    disp(x);
    
    disp("MATRICEA SISTEMULUI");
    disp([A2, b2]);
    
    disp("SOLUTII");
    x = jacobi(A2, b2, ones(1,n)', 0.0000001, 0.000001, 100);
    disp('Jacobi: ');
    disp(x);
    x = sor(A2,b2, ones(1,n)', 1.234, 0.001, 0.001, 100);
    disp('SOR: ');
    disp(x);
    x = gaussSeidal(A2,b2,ones(1,n)',0.001,0.001,100);
    disp('Gauss Seidal: ');
    disp(x);
end