package ch.epfl.bbp.range;

public class CharacterSequence implements Sequence<Character> {

	private char _char;

	public CharacterSequence(char _char) {
		this._char = _char;
	}

	public Sequence<Character> next() {
		return new CharacterSequence((char) (((int) _char) + 1));
	}

	@Override
	public Character value() {
		return _char;
	}

	@Override
	public Sequence<Character> previous() {
		return new CharacterSequence((char) (((int) _char) - 1));
	}
}