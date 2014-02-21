# 1. Split according to populations.
# 2. Extract 300 replicates for each population.

ss=`seq 30 30 210`
for s in ${ss}
do
    let "n=${s}*2"
    let "h=(${n}+4)*300"
    echo "zcat AFR_CEU.sel_yri.n${s}.gz | java -jar -Xmx1G ~/scct/MSIndividualExtractor.V1.0.jar 1 ${n} | head -n ${h} | gzip >yri.id${s}.gz"
    
    let "e1=${n}+1"
    let "e2=${n}*2"
    echo "zcat AFR_CEU.sel_yri.n${s}.gz | java -jar -Xmx1G ~/scct/MSIndividualExtractor.V1.0.jar ${e1} ${e2} | head -n ${h} | gzip >ceu.id${s}.gz"
done
