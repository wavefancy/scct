# Compute derived allele freqency.

# AFR_CEU.neu.n90.gz
ss=`cat ../para.sh`
for s in ${ss}
do
    echo "zcat ../yri.${s}.gz | python ~/scct/MSAlleleFrequency.py | gzip >yri.id${s}.daf.gz"
    echo "zcat ../ceu.${s}.gz | python ~/scct/MSAlleleFrequency.py | gzip >ceu.id${s}.daf.gz"
done
