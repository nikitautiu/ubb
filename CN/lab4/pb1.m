function x = pb1(A,b)
    % eliminare gaussiana
    a = [A b];  % matricea extinsa
    n = rows(A);
    for i = 1:(n-1)
        % gasim pivotul
        [~, p] = max(abs(a(i:n,i)));
        if(p<0)
            error('Solutia nu este unica');
        end

        if(p ~= i)
            % interschimbare
            a([p i],:) = a([i p],:);
        end

        for jj = (i+1):n
            % scalare
            mij = a(jj,i) / a(i,i);
            f = a(jj,:) - mij * a(i,:);
            a(jj,:) = f;
        end

        % aflam solutia
        x(n) = a(n, n+1) / a(n,n);
        for ii = n-1:-1:1
            sum = 0;
            for j = ii+1:n
                sum = sum + a(ii,j)*x(j);
            end
             x(i) = (a(ii,n+1) - sum) / a(ii,ii);
        end

    end
end
