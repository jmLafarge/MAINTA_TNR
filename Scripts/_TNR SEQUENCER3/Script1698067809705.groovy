import tnrTC.*

def sequencerList = [
    [name: 'RO.ACT.001.LEC', rep: 1],
    [name: 'RO.ACT.001.LEC.01', rep: 2],
    [name: 'RO.ACT.001.LEC.02', rep: 3],
    [name: 'RO.FOU.001.LEC.01', rep: 4],
    [name: 'RO.ORG.001.LEC', rep: 5],
    [name: 'RT.MAT.001.LEC.01', rep: 6],
    [name: 'RO.ACT', rep: 7],
    [name: 'RO', rep: 8],
    [name: 'R', rep: 9]
]

Sequencer3.run(sequencerList)




