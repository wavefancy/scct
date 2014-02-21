#!/usr/bin/env python

'''
    Compute ratio and normalized by frequency category averaged value.
    
    @Author: wavefancy@gmail.com
    @Version: 1.0
    
    @Note:
    1. value zero was setted as 0.00001, less than the nonzero minimum values.

    @Version 1.1
    @Since: 2013-11-10
    1.Skip those lines which could not phased to float for derived count or ancestral count.
    
'''

import sys
from math import log

def help():
    sys.stderr.write('''
    -----------------------------------------------
    Compute unstandardized SCCT score
    -----------------------------------------------
    
    @Author: wavefancy@gmail.com
    @Version: 1.1
    
    @Usages:
    para1: Column index for allele frequency.
    para2: Column index for derived group values.
    para3: Column index for ancestral group values.
    para4: scale ratio file name.
    
    @Note:
    1. Add one column for the unstandardized SCCT score.
    2. Column index starts from 1.
    3. Read from stdin and output to stdout.
    -----------------------------------------------
    \n''')
    sys.stderr.close()
    sys.exit(-1)

class P(object):
    mini = 0.00001 #reset 0 as this value
    col_de = 0  #column index for derived group values.
    col_an = 0  #column index for ancestral group values.
    col_fre= 0  #column index for allele frequency.
    
    av_file = ''    #file which contains the frequency category average file.
    
def runApp():
    
    #read frequency specific average values.
    fre_map = {}
    for line in open(P.av_file):
        line = line.strip()
        if line:
            ss = line.split()
            
            try:
                val = float(ss[1])
                fre = ss[0]
                
                fre_map[fre] = val
            except ValueError:
                pass
    
    #process data from stdin
    for line in sys.stdin:
        line = line.strip()
        if line:
            ss = line.split()
            
            #add for version 1.1
            try:
                an = float(ss[P.col_an])
                de = float(ss[P.col_de])
            except ValueError:
                continue
            
            if an == 0:
                an = P.mini
            if de == 0:
                de = P.mini
            try:
                sys.stdout.write('%s\t%.10f\n'%(line, log(de/an/fre_map.get(ss[P.col_fre]))))
                
            except TypeError:
                sys.stderr.write('Warning:\nSkip one variant line, can\'t find frequency map.\n>%s\n'%(line))
                pass
                #sys.stderr.write(fre_map.get(ss[P.col_fre]))
            #sys.stdout.write('%s\t%.10f\n'%(line, log(de/an/fre_map.get(ss[P.col_fre]))))
            
    sys.stdout.close()
    
    
if __name__ == '__main__' :
    if(len(sys.argv) != 5):
        help()
        
    P.col_fre = int(sys.argv[1]) -1
    P.col_de = int(sys.argv[2]) -1
    P.col_an = int(sys.argv[3]) -1
    P.av_file = sys.argv[4]
    
    runApp()
        
