m = load('/home/mz/Documents/epfl/projet/corpora/mesh_cooccurrences');

r = reorder_matrix(m);

image(r(1:200, :));

xlabel('Topics');
ylabel('Meshs');

saveas(gcf, 'pubmed-corr.eps', 'epsc');
