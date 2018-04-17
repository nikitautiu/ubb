function x = pb1(A,b)
    % eliminare gaussiana
    a = [A b];  % matricea extinsa
    n = rows(A);
    for i = 1:(n-1)
        % gasim pivotul
        [~, p] = max(abs(a(i:n, i)));
        p = p + (i - 1); % offset
        if(p<0)
            error('Solutia nu este unica');
        end

        if(p ~= i)
            % interschimbare
            a([p i],:) = a([i p], :);
        end

        for jj = (i+1):n
            % scalare si scadere
            m = a(jj, i) / a(i, i);
            a(jj, :) = a(jj, :) - m * a(i, :);
        end
    end
    if(a(n, n+1) == 0)
      error('Solutia nu este unica');
    end
    
    % aflam solutia
    x(n) = a(n, n+1) / a(n,n);
    for ii = n-1:-1:1
        suma = sum(a(ii, ii+1:n) .* x(ii+1:n));
        x(ii) = (a(ii,n+1) - suma) / a(ii, ii);
    end
end
