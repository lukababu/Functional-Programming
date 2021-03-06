import System.Environment
import System.IO
import Control.Exception
import Control.DeepSeq (force,NFData)
import Control.Exception (try,SomeException)
import Control.Monad
import System.Directory
import Data.List
import Data.String
import System.Exit
import ParseLib
import BruteForce

--The main method will take in command line arguments and read file into a string. 
-- to execute ./filename arg1 arg2
properMachinePenalty :: [String] -> [Bool]
properMachinePenalty [] = [False]
properMachinePenalty (x:xs)
    | elem '.' x = True : properMachinePenalty xs
    | elem ',' x = True : properMachinePenalty xs
    | elem '!' x = True : properMachinePenalty xs
    | elem '?' x = True : properMachinePenalty xs
    | elem '@' x = True : properMachinePenalty xs
    | elem '#' x = True : properMachinePenalty xs
    | elem '%' x = True : properMachinePenalty xs
    | elem '^' x = True : properMachinePenalty xs
    | elem '&' x = True : properMachinePenalty xs
    | elem '(' x = True : properMachinePenalty xs
    | elem ')' x = True : properMachinePenalty xs
    | elem '|' x = True : properMachinePenalty xs
    | elem '\\' x = True : properMachinePenalty xs
    | elem '`' x = True : properMachinePenalty xs
    | elem '~' x = True : properMachinePenalty xs
    | elem '/' x = True : properMachinePenalty xs
    | otherwise = False : properMachinePenalty xs

isValid :: [Bool] -> Bool
isValid x = elem True x

main = do
    args <- getArgs
    handle <- openFile (args!! 0) ReadMode
    contents <- (hGetContents handle)
    --filecontent not contails all of the contents in the file. 
    let filecontent = map noEndSpaces (lines (noCR contents))
        nameSec = sliced "Name:" "forced partial assignment:" filecontent
        forcedSec = sliced "forced partial assignment:" "forbidden machine:" filecontent
        forbidSec = sliced "forbidden machine:" "too-near tasks:" filecontent
        tntSec = sliced "too-near tasks:" "machine penalties:" filecontent
        machPenSec = sliced "machine penalties:" "too-near penalities" filecontent
        tnpSec = sliced "too-near penalities" "" filecontent
    --Now that we have each part of the code sliced from section to section
    --We have to check if the spellings are correct for all the sections
    --If the spellings are incorrectly, the string tokens passed as arguments earlier won't be found 
    --this will result in a creation of an empty string
    --Here we will check if the strings are empty or not
    --this returns "Error while parsing file" if the file does not have the correct input
    --We have now checked for errors in spellings
    --Now we want to check for errors with inputs
    --First, lets check if there is error under name.
    if ((length(delWhitespaceLines nameSec)/=1))
    then do writeFile (last args) "Error while parsing input file"
            exitSuccess
    else return()
    
    --After name, we want to formats of the rest.
    --First we need to ensure the input is a 2-tuple.
    if(anding (map twotup(delWhitespaceLines forcedSec)))
    then return()
    else do writeFile (last args) "invalid machine/task"
            exitSuccess
 
    --Now we can check that forced assignments is actually a valid machine and task pair
    if (anding(map validMTPair(delWhitespaceLines forcedSec)))
    then return()
    else do writeFile (last args) "invalid machine/task"
            exitSuccess
            
    -- look for duplicates
    let forced = parseMT(delWhitespaceLines forcedSec)
    if ((duplicates(map fst forced)) || (duplicates(map snd forced)))
    then do writeFile (last args) "partial assignment error"
            exitSuccess
    else return ()

    --Ensure forbidden machines are a 2-tuple.
    if(anding (map twotup(delWhitespaceLines forbidSec)))
    then return()
    else do writeFile (last args) "invalid machine/task"
            exitSuccess

    --Ensure too near task is a 2-tuple
    if(anding (map twotup(delWhitespaceLines tntSec)))
    then return()
    else do writeFile (last args) "invalid machine/task"
            exitSuccess

    --Ensures too near penalty is a 3-tuple
    if(anding (map tuple3(delWhitespaceLines tnpSec)))
    then return()
    else do writeFile (last args) "invalid machine/task"
            exitSuccess

    --Now we can check that forbidden assignments is actually a valid machine and task pair
    if (anding(map validMTPair(delWhitespaceLines forbidSec)))
    then return()
    else do writeFile (last args) "invalid machine/task"
            exitSuccess

    --Now we check if the too near task is valid
    if (anding(map validTNTPair(delWhitespaceLines tntSec)))
    then return()
    else do writeFile (last args) "invalid task"
            exitSuccess

    --Ensure that the machine penalty is vertically of height 8
    if(length(delWhitespaceLines machPenSec) /= 8)
    then do writeFile (last args) "machine penalty error"
            exitSuccess 
    else return()
    
    --Ensure that the machine penalty is vertically of height 8
    if(isValid (properMachinePenalty machPenSec))
    then do writeFile (last args) "invalid penalty"
            exitSuccess 
    else return()

    let machPen =  (map (map read) (map words (delWhitespaceLines machPenSec)) :: [[Int]])
    --print machPen
    --ensures that the penalty matrix has total elements of 64
    if(sum(map length machPen)/= 64)
    then do writeFile (last args) "machine penalty error"
            exitSuccess 
    else return()
    
    --Now we can check that forbidden assignments is actually a valid machine and task pair
    if (anding(map validTTtrip(delWhitespaceLines tnpSec)))
    then return()
    else do writeFile (last args) "invalid task"
            exitSuccess

    let forcedPartial = parseMT forcedSec
    let forcedMachine = parseMT forbidSec
    let tooNearTask = parseTT (delWhitespaceLines tntSec)
    let penaltyTable = toToupleArray machPen
    let tooNearPenalty = parseTTx tnpSec
    
    let solve1 = solutionSet forcedMachine []
    let solve2 = solutionSetFPA forcedPartial solve1
    let solve3 = solutionSetTNT tooNearTask solve2
    
    let solve4 = calculatePenalty penaltyTable solve3
    let solve5 = solutionSetTTx tooNearPenalty solve4
    let solve6 = minim solve5
    --putStr forcedSec
    writeFile (last args) (solution  solve6)