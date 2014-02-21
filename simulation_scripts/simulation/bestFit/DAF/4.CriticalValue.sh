# Compute critical value

p=(0.316667 0.291666 0.272222 0.258333 0.25 0.241667 0.235714)
ss=(`seq 30 30 210`)
out="critical.txt"
>${out}
for((i=1;i<${#ss[@]};i++)){

    echo ${ss[i]} >>${out}
    zcat yri_ceu.daf.id${ss[i]}.gz | node ~/scct/ComputeWindowedCriticalValue.js  51 51 ${p[i]} 4 >>${out}
    echo "" >>${out}

}
