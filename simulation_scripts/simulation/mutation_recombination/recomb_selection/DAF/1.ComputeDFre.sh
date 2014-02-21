# Compute derived allele freqency.

ss=`cat ../para.sh`
for s in ${ss}
do
    echo "zcat ../r300_yri.${s}.gz | python ~/scct/MSAlleleFrequency.py | gzip >yri.${s}.daf.gz"
    echo "zcat ../r300_ceu.${s}.gz | python ~/scct/MSAlleleFrequency.py | gzip >ceu.${s}.daf.gz"
done
