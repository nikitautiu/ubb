function R = pb4(A)
	% descompunere Choelsky
	[m, n] = size(A);

	for k=1:m
		if (A(k, k) <= 0)
			error("Matrice ne-hermetiana pozitiv definitia");
		end

		for j = k+1:m
			A(j, j:m) = A(j, j:m) - A(k, j:m) * A(k, j) / A(k, k);
		end

		A(k, k:m) = A(k, k:m) / sqrt(A(k, k));
	end

	R = triu(A);
end
