class NumEncoding:
    _chars = {'.': 75, ',': 76, '!': 77, '?': 78, "'": 79, ' ': 80}
    _chars_reverse = {75: '.', 76: ',', 77: '!', 78: '?', 79: "'", 80: ' '}
    _lang_change = 74

    _default_lang_type = 0
    _lower_letter = 0
    _upper_letter = 32
    _digit_letter = 64
    _ru_letters = ['а', 'А']
    _en_letters = ['a', 'A']

    @classmethod
    def encoding(cls, s: str):
        """Преобразует исходную строку в строку чисел в десятичной системе счисления"""
        n = ''
        lang_type = cls._default_lang_type  # русский первоначально
        for c in s:
            if not cls._chars.get(c) is None:
                n += str(100+cls._chars.get(c))[1:]
                continue

            if 0 <= ord(c.lower()) - ord(cls._en_letters[0]) < 28:
                if lang_type != 1:
                    lang_type = 1
                    n += str(cls._lang_change + 100)[1:]
                if c.islower(): n += str(ord(c) - ord(cls._en_letters[0]) + cls._lower_letter + 100)[1:]
                else: n += str(ord(c) - ord(cls._en_letters[1]) + cls._upper_letter + 100)[1:]
            elif 0 <= ord(c.lower()) - ord(cls._ru_letters[0]) < 32:
                if lang_type != 0:
                    lang_type = 0
                    n += str(cls._lang_change + 100)[1:]
                if c.islower(): n += str(ord(c) - ord(cls._ru_letters[0]) + cls._lower_letter + 100)[1:]
                else: n += str(ord(c) - ord(cls._ru_letters[1]) + cls._upper_letter + 100)[1:]
            elif c.isdigit():
                n += str(int(c)+cls._digit_letter + 100)[1:]
            else:
                raise Exception('Char ' + c + ' not found')
        return '1' + str(n)

    @classmethod
    def decoding(cls, n):
        """Преобразует исходную строку из чисел в нормальный вид"""
        s = ''
        n = n[1:]
        lang_type = cls._default_lang_type
        while len(n) > 1:
            c = int(n[0] + n[1])
            n = n[2:]

            if c == cls._lang_change:  # смена языка
                if lang_type != 0: lang_type = 0
                else: lang_type = 1
                continue

            if not cls._chars_reverse.get(c) is None:
                a = cls._chars_reverse.get(c)
                s += a[0]
                continue

            if cls._lower_letter <= c < cls._upper_letter:
                if lang_type == 0: s += chr(c - cls._lower_letter + ord(cls._ru_letters[0]))
                elif lang_type == 1: s += chr(c - cls._lower_letter + ord(cls._en_letters[0]))
            elif cls._upper_letter <= c < cls._digit_letter:
                if lang_type == 0: s += chr(c - cls._upper_letter + ord(cls._ru_letters[1]))
                elif lang_type == 1: s += chr(c - cls._upper_letter + ord(cls._en_letters[1]))
            elif cls._digit_letter <= c < cls._digit_letter+10:
                s += str(c - cls._digit_letter)
            else:
                raise Exception('Invalid character number ' + str(n))
        return str(s)
