#!/usr/bin/env python

'''
    Compute Empirical Alpha, preparation for normalizing.
    
    @Author: wavefancy@gmail.com
    @Version: 1.0
    
    @Algorithms:
    1.Skip those lines which could not phased to float for derived count or ancestral count.
    2.Compute ratio of sum, instead of mean of ratio.
    
'''

import sys

def help():
    sys.stderr.write('''
    ----------------------------------------
    Compute Empirical Alpha (ratio of sum)
    ----------------------------------------
    
    @Author: wavefancy@gmail.com
    @Version: 1.0
    
    @Usages:
    para1: Column index for frequency
    para2: Column index for derived group values.
    para3: Column index for ancestral group values.
    
    @Note:
    1. Column index starts from 1.
    2. Read from stdin, and output to stdout.
    3. Tital [Fre   Ratio   Count]
    ----------------------------------------
    \n''')
    sys.stderr.close()
    sys.exit(-1)

class P(object):
    col_freq = 0    #column index for frequency
    col_de = 0  #column index for derived group values.
    col_an = 0  #column index for ancestral group values.
    
if __name__ == '__main__':
    if(len(sys.argv) != 4):
        help()
    
    P.col_freq = int(sys.argv[1]) -1
    P.col_de = int(sys.argv[2]) -1
    P.col_an = int(sys.argv[3]) -1
    
    #computing starts.
    fre_map = {} # fre --> [totalDerived,totalAncestral,count]
    
    for line in sys.stdin:
        line = line.strip()
        if line:
            ss = line.split()
            fre = ss[P.col_freq]
            
            #add for version 1.1, skip can't parse line.
            try:
                an = float(ss[P.col_an])
                #if(an == 0):
                #    continue
                
                de = float(ss[P.col_de])
            except ValueError:
                continue
            
            if fre_map.has_key(fre):
                t = fre_map.get(fre)
                t[0] += de
                t[1] += an
                t[2] += 1
                #update
                fre_map[fre] = t
            else:
                fre_map[fre] = [de,an,1]
                
    #print(fre_map)
    #Output results.
    keys = zip(fre_map.keys(),map(float, fre_map.keys()))
    keys = sorted(keys,key=lambda t:t[1])
    #keys = map(str,keys)
    sys.stdout.write('Fre\tExpectedRatio\tCount\n')
    for key in keys:
        key = key[0]
        val = fre_map[key]
        ratio = val[0]/val[1]
        sys.stdout.write('%s\t%.10f\t%d\n'%(key, ratio, val[2]))
    
    sys.stdout.flush()
    sys.stdout.close()
    
                
            