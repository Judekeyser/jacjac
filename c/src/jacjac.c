/* String Size module
 * ==================
 * Compute the length of a short C-String.
 */


int jacjac_string_size(const void *);


#ifdef JACJAC_IMPL

int jacjac_string_size(const void *data)
{
  const char *string = data;
  int length = 0;
  while(length < 32767) {
    if(*string) {
      string++;
	  length++;
    } else return length;
  }
  return -1;
}

#endif
