

import my.InfoBDD
import my.TCFiles
import my.Sequencer



if (InfoBDD.map.isEmpty()) { InfoBDD.load() }
if (TCFiles.TCfileMap.isEmpty()) { TCFiles.load() }
if (Sequencer.testCasesList.isEmpty()) { Sequencer.load() }


