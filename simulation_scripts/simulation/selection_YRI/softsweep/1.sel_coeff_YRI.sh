# Simulate the selection model of YRI and CEU
# Softsweep in YRI population

# Ne = 100000, u = 1.5 * 10^-8, r = 1.3 * 10^-8. L = 3m.
# theta = 4*Ne*u*L = 4*100000*1.5*10^-8*3*10^6 = 18000
# p = 4*Ne*r*L = 4 * 100000 * 1.3 * 10^-8 * 3 * 10^6 = 15600

# AF expansion:
# 200g/(4*100000) = 0.0005
# Ne_Africa_before expansion 24000 = 0.24

# EU expansion:
# 350/(4*100000) = 0.000875
# Ne_European_before expansion 7700 = 0.077

# EU out of africa: 3500/(4*100000) = 0.00875

# African ancestral expansion:
# 17000/(4*100000) = 0.0425
# Ne_before expansion: 12500 = 0.125

# Selection in YRI
# time: 0.005 = 2000/4/100000
# start freq. 0.0001,0.001,0.01,0.1, in YRI (soft sweep)
# Selection coefficient: 1300


ss="0.0001 0.001 0.01 0.1"
for s in ${ss}
do
    sAA=1300
    let "saA=${sAA}/2"
    echo "java -jar -Xmx15G ~/scct/msms.jar 240 500 -t 18000 -r 15600 -N 100000 -I 2 120 120 -en 0.0005 1 0.24 -en 0.000875 2 0.077 -ej 0.00875 2 1 -en 0.0425 1 0.125 -SI 0.005 2 ${s} 0 -SAA ${sAA} -SaA ${saA} -Sp 0.5 -Smark -SFC -threads 30 | java -Xmx1G -jar ~/scct/MsDataSetSelectorV1.0.jar 0.5000000000 0.0025 1 |gzip > AFR_CEU.sel_yri.${s}.gz"
done
