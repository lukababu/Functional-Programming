import System.Environment
import System.IO  
import Control.Monad  
import System.Directory  
import Data.List  
import Data.String
import System.Exit
import ParseLib

--The main method will take in command line arguments and read file into a string. 
-- to execute ./filename arg1 arg2

main = do
    args <- getArgs
    handle <- openFile (args!! 0) ReadMode
    contents <- (hGetContents handle)
    --filecontent not contails all of the contents in the file. 
    let filecontent = map noEndSpaces (lines (noCR contents))
    --putStr $ unlines filecontent
        --Now we split it into sections
        --First sections that is sliced is name
        nameSec = sliced "Name:" "forced partial assignment:" filecontent
        --Forced partial Assignment
        forcedSec = sliced "forced partial assignment:" "forbidden machine:" filecontent
        --Fobidden machines
        forbidSec = sliced "forbidden machine:" "too-near tasks:" filecontent
        --Too near Task
        tntSec = sliced "too-near tasks:" "machine penalties:" filecontent
        --Machine pentalties
        machPenSec = sliced "machine penalties:" "too-near penalities" filecontent
        --too near penalties
        tnpSec = sliced "too-near penalities" "" filecontent
    --Now that we have each part of the code sliced from section to section
    --We have to check if the spellings are correct for all the sections
    --If the spellings are incorrectly, the string tokens passed as arguments earlier won't be found 
    --this will result in a creation of an empty string
    --Here we will check if the strings are empty or not
    --this returns "Error while parsing file" if the file does not have the correct input
    if (elem 0 (map length [nameSec, forcedSec, forcedSec, tnpSec, machPenSec, tnpSec]))
    then do writeFile (last args) "Error while parsing input file"
            exitSuccess
    else return ()
    --We have now checked for errors in spellings
    --Now we want to check for errors with inputs
    --First, lets check if there is error under name.
    if ((length(delWhitespaceLines nameSec)/=1))
    then do writeFile (last args) "Error while parsing input file"
            exitSuccess
    else return()
    --let aaa  = "BBB" ++ "CCC"
    --writeFile (last args) (unlines nameSec)
