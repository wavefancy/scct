
#compute the unstanderdized scct score.
zcat counts.gz | grep -i 'ok' | python ComputeUnstandardizedSCCTV1.1.py 2 3 4 scale_ratio.txt | gzip >un.scct.gz