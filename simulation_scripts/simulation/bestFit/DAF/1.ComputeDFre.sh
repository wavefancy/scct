# Compute derived allele freqency.

# AFR_CEU.neu.n90.gz
ss=`seq 30 30 210`
for s in ${ss}
do
    echo "zcat ../yri.id${s}.gz | python ~/scct/MSAlleleFrequency.py | gzip >yri.id${s}.daf.gz"
    echo "zcat ../ceu.id${s}.gz | python ~/scct/MSAlleleFrequency.py | gzip >ceu.id${s}.daf.gz"
done
