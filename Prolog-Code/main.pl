:-dynamic(inputFile/1, outputFile/1, buffer/1, throwException/1).
:- dynamic(partialAssignment/2).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%                Error Codes               %%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
error(nil, 'file parsed without problems').
error(-10, 'Error while parsing input file').
error(-11, 'invalid task').
error(-12, 'invalid penalty').
error(-13, 'partial assignment error').
error(-14, 'invalid machine/task').
error(-15, 'machine penalty error').
error(-16, 'No valid solution possible!').

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%            Tasks to Number               %%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
asciiTable([65|X], 'A', X).
asciiTable([66|X], 'B', X).
asciiTable([67|X], 'C', X).
asciiTable([68|X], 'D', X).
asciiTable([69|X], 'E', X).
asciiTable([70|X], 'F', X).
asciiTable([71|X], 'G', X).
asciiTable([72|X], 'H', X).


:- initialization(main).

main:-
    % CMD Arguments
    retractall(inputFile(_)),
    argument_value(1, InputFile),
    asserta(inputFile(InputFile)),

    retractall(outputFile(_)),
    argument_value(2, OutputFile),
    asserta(outputFile(OutputFile)),
    
    retractall(buffer(_)),
    retractall(throwException(_)),
    retractall(partialAssignment(_,_)),
    
    % Open File & parse
    see(InputFile),
    read_file(1,Contents),
    seen,
    deleteLastElement(Contents, Contents_),
    append(Contents_," ", Contents_1),
    asserta(buffer(Contents_1)),
    asserta(throwException(nil)),
    parse,
    
    % Output
    throwException(X),!,
    error(X,Output),
    write_file(OutputFile,Output),!.
  
deleteLastElement([_], []).
deleteLastElement([Head, Next|Tail], [Head|NTail]):-
deleteLastElement([Next|Tail], NTail).

read_file(-1,[]).   % End of file
read_file(_,Y):-
    get0(Z),
    read_file(Z,W),
    Y = [Z|W].

write_file(X,Y):-
    tell(X),
    write(Y),
    told,!.

parse :-
    parse_.
parse :-
    throwException(nil),
    retract(throwException(nil)),
    asserta(throwException(-10)).
parse.

parse_ :-
    buffer(X),!,
    machineName(X, R1),!,
    forcedPartialAssignment(R1, R2).

%       Argument 1: the tag to match
%       Argument 2: the content to match the tag
%       Argument 3: the content without the tag*/
tagRemove([], Content, Content).
tagRemove([Tag|[]], [Tag|Content], Content).
tagRemove([Tag|X], [Tag|Y], Z) :-
    tagRemove(X, Y, Z).
    
machineName([], []) :-
    throwException(nil),
    retract(throwException(nil)),
    asserta(throwException(-10)).
machineName(Content, Return) :- 
  tagRemove("Name:", Content, Trimmed),!,
  line_end(Trimmed, Trimmed_),!,
  getTrimmedLine(Trimmed_, _, Trimmed_1),!,
  line_end(Trimmed_1, Return).

forcedPartialAssignment([], []) :-
  throwException(nil),
  retract(throwException(nil)),
  asserta(throwException(-10)).
forcedPartialAssignment(Content, Return) :-
  tagRemove("forced partial assignment:", Content, Trimmed),!,
  line_end(Trimmed, Trimmed_),!,
  parsePartialAssignments(Trimmed_, Return).

/* Remove whitespaces at the end of line, and new line code */
line_end(X, Return) :-
    tagRemove(" ", X, Return_),
    line_end(Return_, Return).
line_end(X, Return) :-
    tagRemove("\r",X,Return_),
    line_end(Return_,Return).
line_end(X, Return) :- tagRemove("\n", X, Return).

getTrimmedLine(I, O, R):-
  getLine(I, Line, R),!,
  checkCharAndReadRest(Line, O).

getLine([],[],[]).
getLine([10|I], [], I).
getLine([C|I], [C|Next], R) :-
  getLine(I, Next, R).

checkCharAndReadRest([],[]).
checkCharAndReadRest([9], []).  % horizontal tab
checkCharAndReadRest([10], []). % new line
checkCharAndReadRest([13], []). % carriage return
checkCharAndReadRest([32], []). % blank character
% horizontal tab
checkCharAndReadRest([9|T], []) :-
  checkCharAndReadRest(T, []).
% new line
checkCharAndReadRest([10|T], []) :-
  checkCharAndReadRest(T, []).
% carriage return
checkCharAndReadRest([13|T], []) :-
  checkCharAndReadRest(T, []).
% blank character
checkCharAndReadRest([32|T], []) :-
  checkCharAndReadRest(T, []).
checkCharAndReadRest([H|T], [H|O]) :-
  checkCharAndReadRest(T, O).
  
%------------------------------------------------------------------------------
% Forced partial assignments.
%------------------------------------------------------------------------------
parsePartialAssignments(Input, Return) :-
  getTrimmedLine(Input, [], Return).
parsePartialAssignments(Input, Return) :-
  getTrimmedLine(Input, Line, Return_),!,
  parsePartialAssignment(Line),!,
  parsePartialAssignments(Return_, Return).

parsePartialAssignment(Input) :-
  parsePartialAssignment_(Input),!.
parsePartialAssignment(_) :-
  throwException(nil),
  retract(throwException(nil)),
  asserta(throwException(-13)).

parsePartialAssignment_(Input) :-
  throwException(nil),!,
  machineTask(Input, Machine, Task),!,
  \+ partialAssignment(Machine,X),!,
  \+ partialAssignment(Y,Task),!,
  assertz(partialAssignment(Machine,Task)), !.

machineTask(Line, Machine, Task) :-
  machineTask_(Line, Machine, Task).
machineTask(_, 0, 0) :-
  throwException(nil),
  retract(throwException(nil)),
  asserta(throwException(-10)).
machineTask(_, 0, 0).
  
machineTask_(Line, Machine, Task) :-
  tagRemove("(", Line, X),!,
  notSpace(X),!,
  machineNumber(X, Machine, Y),!,
  tagRemove(",", Y, Z),!,
  notSpace(Z),!,
  taskNumberConstraint(Z, Task, W),!,
  tagRemove(")", W, []),!.
  
  
notSpace(X) :- \+ isSpace(X).
isSpace([9|X]).
isSpace([10|X]).
isSpace([13|X]).
isSpace([32|X]).

machineNumber([],_, _) :-
  throwException(nil),
  retract(throwException(nil)),
  asserta(throwException(-14)).
machineNumber(I, Machine, R) :- 
  throwException(nil),
  number(I, Machine, R),
  Machine < 9,
  Machine > 0.
machineNumber(_, _, []) :-
  throwException(nil),
  retract(throwException(nil)),
  asserta(throwException(-14)).

number([Head|Tail], Machine, R) :-
  throwException(nil),
  isDigit(Head),!,
  number_([Head|Tail], [], N, R),!,
  number_codes(Machine, N),!.

number_([Head|Tail], SOFAR, O, R) :-
  throwException(nil),
  isDigit(Head),
  append(SOFAR, [Head], NEXT),
  number_(Tail, NEXT, O, R).
number_(Tail, SOFAR, SOFAR, Tail) :-
  throwException(nil).

isDigit(N) :- N > 47,!, N < 58.

taskNumberConstraint(Integer, Task, T) :-
  throwException(nil),
  asciiTable(Integer, Task, T).
taskNumberConstraint(_, _, []) :-
  throwException(nil),
  retract(throwException(nil)),
  asserta(throwException(-14)).


