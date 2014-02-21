# Compute critical value

#p=(0.3 0.3 0.272222 0.258333 0.25 0.241667 0.235714)
ss=(`seq 30 30 210`)
out="critical_0.3.txt"
>${out}
for((i=1;i<${#ss[@]};i++)){

    echo ${ss[i]} >>${out}
    zcat yri_ceu.daf.id${ss[i]}.gz | node ~/scct/ComputeWindowedCriticalValue.js  51 51 0.3 4 >>${out}
    echo "" >>${out}

}
